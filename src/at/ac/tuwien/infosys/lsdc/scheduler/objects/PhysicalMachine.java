package at.ac.tuwien.infosys.lsdc.scheduler.objects;

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
	
	private transient Boolean isRunning = false;
	private transient HashMap<Integer,VirtualMachine> vms = new HashMap<Integer,VirtualMachine>();
	
	private transient Integer currentMaxVMId = 0;
	
	public VirtualMachine startVirtualMachine(Integer neededDiskMemory,Integer neededMemory, Integer neededCPUS) {
		usedCPUs += neededCPUS;
		usedMemory += neededMemory;
		usedDiskMemory += neededDiskMemory;
		
		VirtualMachine vm = new VirtualMachine(this,createVMId(),neededDiskMemory, neededMemory, neededCPUS);
		vms.put(vm.getId(), vm);
		return vm;
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
	public Boolean isRunning() {
		return isRunning;
	}
	public void setPricePerCycle(Integer pricePerCycle) {
		this.pricePerCycle = pricePerCycle;
	}
	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
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
				+ pricePerCycle + ", isRunning=" + isRunning + "]";
	}
	
}
