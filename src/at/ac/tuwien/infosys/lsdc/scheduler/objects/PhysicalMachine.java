package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import java.util.ArrayList;
import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.IResourceInformation;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;


public class PhysicalMachine extends Machine implements IResourceInformation{
	private Integer id = null;
	private Integer CPUs = null;
	private Integer memory = null;
	private Integer diskMemory = null;
	private Integer pricePerCycle = null;
	
	private transient Integer usedCPUs = 0;
	private transient Integer usedMemory = 0;
	private transient Integer usedDiskMemory = 0;
	
	private transient HashMap<Integer,VirtualMachine> virtualMachines = new HashMap<Integer,VirtualMachine>();
	
	private transient Integer currentMaxVMId = 0;
	
	public VirtualMachine startVirtualMachine(Integer neededDiskMemory,Integer neededMemory, Integer neededCPUS) {
		usedCPUs += neededCPUS;
		usedMemory += neededMemory;
		usedDiskMemory += neededDiskMemory;
		
		VirtualMachine vm = new VirtualMachine(this,createVMId(),neededDiskMemory, neededMemory, neededCPUS);
		virtualMachines.put(vm.getId(), vm);
		return vm;
	}
	
	public HashMap<Integer, VirtualMachine> getVirtualMachines() {
		return virtualMachines;
	}

	private Integer createVMId() {
		return ++currentMaxVMId;
		
	}
	
	public Resource getUsedResources() {
		return new Resource(id, usedCPUs, usedMemory, usedDiskMemory);
	}
	
	public Resource getTotalResources() {
		return new Resource(id, CPUs, memory, diskMemory);
	}

	
	public Integer getPricePerCycle() {

		return pricePerCycle;
	}


	public void setPricePerCycle(Integer pricePerCycle) {
		this.pricePerCycle = pricePerCycle;
	}
	
	public Integer getCPUs() {
		return CPUs;
	}
	
	public Integer getMemory() {
		return memory;
	}
	
	public Integer getDiskMemory() {
		return diskMemory;
	}
	
	public void setCPUs(Integer cPUs) {
		CPUs = cPUs;
	}
	
	public void setMemory(Integer memory) {
		this.memory = memory;
	}
	public void setDiskMemory(Integer diskMemory) {
		this.diskMemory = diskMemory;
	}
	
	public boolean canHostJob(InsourcedJob job) {
		return job.getConsumedCPUs() <= (CPUs - usedCPUs) && 
				job.getConsumedMemory() <= (memory - usedMemory) &&
				job.getConsumedDiskMemory() <= (diskMemory - usedDiskMemory);
	}
	
	public ArrayList<VirtualMachine> getVirtualHostingCandidates(InsourcedJob job) {
		ArrayList<VirtualMachine> candidates = new ArrayList<VirtualMachine>();
		for (VirtualMachine currentCandidate : virtualMachines.values()) {
			if (currentCandidate.canHostJob(job)) {
				candidates.add(currentCandidate);
			}
		}
		return candidates;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "PhysicalMachine [CPUs=" + CPUs + ", memory=" + memory
				+ ", diskMemory=" + diskMemory + ", pricePerCycle="
				+ pricePerCycle + "]";
	}	
}
