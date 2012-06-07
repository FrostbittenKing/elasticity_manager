package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.ICloudClusterManager;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class JobScheduler implements IJobCompletionCallBack{
	private static JobScheduler instance = null;
	
	/*
	private ArrayList<PhysicalMachine> physicalMachines = new ArrayList<PhysicalMachine>();
	private ArrayList<VirtualMachine> virtualMachines = new ArrayList<VirtualMachine>();
	
	private Integer currentUsedMemory = 0;
	private Integer currentUsedCPUs = 0;
	private Integer currentUsedDiskMemory = 0;
	
	private Integer totalMemory = null;
	private Integer totalCPUs = null;
	private Integer totalDiskMemory = null;
	
	private Integer currentCycleCosts = null;
	*/
	private ICloudClusterManager cloudCluster = null;
	
	private JobScheduler(){
		
	}
	
	public void initialize(ICloudClusterManager cloudCluster) {
		this.cloudCluster = cloudCluster;
	}
	
/*	public void initialize(HashMap<Integer,PhysicalMachine> physicalMachines){
		totalMemory = 0;
		totalCPUs = 0;
		totalDiskMemory = 0;
		currentCycleCosts = 0;
		
		this.physicalMachines.clear();
		PhysicalMachine [] machines = (PhysicalMachine [])physicalMachines.values().toArray();
		
		
		for (PhysicalMachine currentMachine : machines){
			this.physicalMachines.add(currentMachine);
			totalCPUs += currentMachine.getCPUs();
			totalDiskMemory += currentMachine.getDiskMemory();
			totalMemory += currentMachine.getMemory();
			System.out.println("Added phyiscal machine: " + currentMachine);
		}
		
	}*/
	
	public synchronized void scheduleJob(Job job){
		System.out.println("Scheduled job: " + job + " , WOOHOO!");
		
		if (cloudCluster.jobFits(job)) {
/*		if (totalCPUs - currentUsedCPUs >= job.getConsumedCPUs() &&
			totalDiskMemory - currentUsedDiskMemory >= job.getConsumedDiskMemory() &&
			totalMemory - currentUsedMemory >= job.getConsumedMemory()){
			//job might be fit in somewhere, main scheduling algorithm takes place here
*/
		}
		else{
			//TODO: job doesn't fit, outsource to other cloud
		}
		/* TODO:
		 * check if there is enough memory, diskmemory, cpus to fit new job
		 * if yes, do fancy scheduling shit with bin packing
		 * if physical machine needs to be started, add its cost per cycle to currentCycleCosts
		 * if a physical machine is shut down, remove the costs
		 * if not, outsource job to other cloud
		 * finally, if job was able to be scheduled locally, add job diskmemory,
		 * memory, cpu requirements to currentusage, then call VirtualMachine.addJob(job) 
		 */
	}
	
	public static JobScheduler getInstance(){
		if (instance == null){
			instance = new JobScheduler();
		}
		return instance;
	}

	@Override
	public synchronized void completeJob(Job job) {
	/*	currentUsedCPUs -= job.getConsumedCPUs();
		currentUsedDiskMemory -= job.getConsumedDiskMemory();
		currentUsedMemory -= job.getConsumedMemory();
		*/
	}

}
