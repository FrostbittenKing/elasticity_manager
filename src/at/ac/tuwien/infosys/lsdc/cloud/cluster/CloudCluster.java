package at.ac.tuwien.infosys.lsdc.cloud.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.bus.BusListenerClientException;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.exceptions.PhysicalMachineException;
import at.ac.tuwien.infosys.lsdc.scheduler.IJobEventListener;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;

public class CloudCluster implements IJobEventListener{

	private HashMap<Integer, PhysicalMachine> physicalMachines = null;
	private HashMap<Integer, PhysicalMachine> runningMachines = null;
	private HashMap<Integer, PhysicalMachine> offlineMachines = null;
	
	
	// store the overall available resources in the cloud, as long as they are enough for a job, it should
	// be possible to migrate some jobs to other machines to fit the waiting job in somewhere
	private Integer currentUsedMemory = 0;
	private Integer currentUsedCPUs = 0;
	private Integer currentUsedDiskMemory = 0;
	
	private Integer totalMemory = 0;
	private Integer totalCPUs = 0;
	private Integer totalDiskMemory = 0;
	
	private Integer currentCycleCosts = 0;
	
	public CloudCluster(HashMap<Integer,PhysicalMachine> physicalMachines) {
		this.physicalMachines = physicalMachines;
		this.offlineMachines = new HashMap<Integer, PhysicalMachine>();
		this.runningMachines = new HashMap<Integer, PhysicalMachine>();
		PhysicalMachine [] machines = physicalMachines.values().toArray(new PhysicalMachine [physicalMachines.values().size()]);
		
		for (PhysicalMachine currentMachine : machines) {
			totalCPUs += currentMachine.getCPUs();
			totalDiskMemory += currentMachine.getDiskMemory();
			totalMemory += currentMachine.getMemory();
			
			offlineMachines.put(currentMachine.getId(), currentMachine);
		}
		
	}

	public void addPhysicalMachine(PhysicalMachine machine) {
		this.physicalMachines.put(machine.getId(), machine);
		this.offlineMachines.put(machine.getId(), machine);
	}
	
	private Integer[] getRegisteredMachines() {
		return (Integer []) physicalMachines.keySet().toArray();
	}

	public Boolean jobFits(Job job) {
		return (totalCPUs - currentUsedCPUs >= job.getConsumedCPUs() &&
		totalDiskMemory - currentUsedDiskMemory >= job.getConsumedDiskMemory() &&
		totalMemory - currentUsedMemory >= job.getConsumedMemory());
	}

	public void startMachineForJob(Job job) {
		//TODO: start a machine
	}
	
	public void stopMachine(PhysicalMachine pm) throws PhysicalMachineException{
		if (!runningMachines.values().contains(pm)){
			throw new PhysicalMachineException("Could not stop: Physical machine with id: " + pm.getId() + " is not running.");
		}
		if (pm.getVirtualMachines().size() != 0){
			throw new PhysicalMachineException("Could not stop: Physical machine with id: " + pm.getId() + " has running virtual machines");
		}
		runningMachines.remove(pm.getId());
		offlineMachines.put(pm.getId(), pm);
	}

	public PhysicalMachine getPhysicalMachine(Integer id) {
		return physicalMachines.get(id);
	}

	public PhysicalMachine[] getRunningMachines() {
		return runningMachines.values().toArray(new PhysicalMachine[runningMachines.values().size()]);
	}

	public ArrayList<PhysicalMachineUsage> getUsage() {
		ArrayList<PhysicalMachineUsage> physicalMachineUsage = new ArrayList<PhysicalMachineUsage>();
		for (PhysicalMachine currentPM : runningMachines.values()){
			physicalMachineUsage.add(new PhysicalMachineUsage(currentPM.getUsedResources(), currentPM.getTotalResources()));
					
		}
		return physicalMachineUsage;
	}

	@Override
	public void jobAdded(Job job) {
		currentUsedCPUs += job.getConsumedCPUs();
		currentUsedDiskMemory += job.getConsumedDiskMemory();
		currentUsedMemory += job.getConsumedMemory();		
	}	
	
	@Override
	public void jobCompleted(Job job) {
		currentUsedCPUs -= job.getConsumedCPUs();
		currentUsedDiskMemory -= job.getConsumedDiskMemory();
		currentUsedMemory -= job.getConsumedMemory();
	}
}
