package at.ac.tuwien.infosys.lsdc.cloud.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.bus.BusListenerClientException;
import at.ac.tuwien.infosys.lsdc.scheduler.IJobCompletionCallBack;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public class CloudCluster implements ICloudCluster,IJobCompletionCallBack {

	private HashMap<Integer,PhysicalMachine> physicalMachines = null;
	
	
	// store the overall available ressources in the cloud, as long as they are enough for a job, it should
	// be possible to migrate some jobs to other machines to fit the waiting job in somewhere
	private Integer currentUsedMemory = 0;
	private Integer currentUsedCPUs = 0;
	private Integer currentUsedDiskMemory = 0;
	
	private Integer totalMemory = null;
	private Integer totalCPUs = null;
	private Integer totalDiskMemory = null;
	
	private Integer currentCycleCosts = null;
	
	public CloudCluster(HashMap<Integer,PhysicalMachine> physicalMachines) {
		this.physicalMachines = physicalMachines;
		PhysicalMachine [] machines = (PhysicalMachine [])physicalMachines.values().toArray();
		
		for (PhysicalMachine currentMachine : machines) {
			totalCPUs += currentMachine.getCPUs();
			totalDiskMemory += currentMachine.getDiskMemory();
			totalMemory += currentMachine.getMemory();
		}
		
	}

	@Override
	public void addPhysicalMachine(PhysicalMachine machine) {
		this.physicalMachines.put(machine.getId(), machine);	
	}
	
	private Integer[] getRegisteredMachines() {
		return (Integer []) physicalMachines.keySet().toArray();
	}

	@Override
	public synchronized void addJob(Integer machineId, Job job) {
		physicalMachines.get(machineId);
		//TODO start virtual machine in physical machine and put job in it
	}

	@Override
	public Boolean jobFits(Job job) {
		return (totalCPUs - currentUsedCPUs >= job.getConsumedCPUs() &&
		totalDiskMemory - currentUsedDiskMemory >= job.getConsumedDiskMemory() &&
		totalMemory - currentUsedMemory >= job.getConsumedMemory());
	}

	@Override
	public synchronized void completeJob(Job job) {
		currentUsedCPUs -= job.getConsumedCPUs();
		currentUsedDiskMemory -= job.getConsumedDiskMemory();
		currentUsedMemory -= job.getConsumedMemory();
		
	}
	
	
	
}
