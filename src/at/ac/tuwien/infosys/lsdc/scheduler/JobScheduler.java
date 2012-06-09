package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.Set;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.ICloudClusterManager;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;

public class JobScheduler implements IJobCompletionCallBack{
	private static JobScheduler instance = null;
	
	private ICloudClusterManager cloudCluster = null;
	
	private JobScheduler(){
		
	}
	
	public void initialize(ICloudClusterManager cloudCluster) {
		this.cloudCluster = cloudCluster;
	}
	
	
	public synchronized void scheduleJob(Job job){
		System.out.println("Scheduled job: " + job + " , WOOHOO!");
		
		if (cloudCluster.jobFits(job)) {

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

	public synchronized void completeJob(Job job) {
	/*	currentUsedCPUs -= job.getConsumedCPUs();
		currentUsedDiskMemory -= job.getConsumedDiskMemory();
		currentUsedMemory -= job.getConsumedMemory();
		*/
	}

}
