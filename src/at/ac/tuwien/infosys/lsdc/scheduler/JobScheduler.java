package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class JobScheduler implements IJobCompletionCallBack{
	private static JobScheduler instance = null;
	
	private ArrayList<PhysicalMachine> physicalMachines = new ArrayList<PhysicalMachine>();
	private ArrayList<VirtualMachine> virtualMachines = new ArrayList<VirtualMachine>();
	
	private Integer currentUsedTotalMemory = 0;
	private Integer currentUsedTotalCPUs = 0;
	private Integer currentUsedTotalDiskMemory = 0;
	
	private Integer totalAvailableMemory = 0;
	private Integer totalAvailableCPUs = 0;
	private Integer totalAvailableDiskMemory = 0;
	
	private JobScheduler(){
		
	}
	
	public void initialize(ArrayList<PhysicalMachine> physicalMachines){
		for (PhysicalMachine currentMachine : physicalMachines){
			this.physicalMachines.add(currentMachine);
			totalAvailableCPUs += currentMachine.getCPUs();
			totalAvailableDiskMemory += currentMachine.getDiskMemory();
			totalAvailableMemory += currentMachine.getMemory();
			System.out.println("Added phyiscal machine: " + currentMachine);
		}
	}
	
	public synchronized void scheduleJob(Job job){
		System.out.println("Scheduled job: " + job + " , WOOHOO!");
		//TODO: magic happens here
		
		/* TODO:
		 * check if there is enough memory, diskmemory, cpus to fit new job
		 * if yes, do fancy scheduling shit with bin packing
		 * if not, outsource job to other cloud
		 * finally, if job was able to be scheduled locally, add job diskmemory, memory, cpu requirements to currentusage, then call (new Thread(job)).run() 
		 * 
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
		//TODO: remove job from virtual machine? or maybe let virtual machine do it itself
		currentUsedTotalCPUs -= job.getConsumedCPUs();
		currentUsedTotalDiskMemory -= job.getSize();
		currentUsedTotalMemory -= job.getConsumedMemory();
	}

}
