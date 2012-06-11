package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public class PerformanceMonitor implements IJobEventListener {

	private static PerformanceMonitor instance;

	private HashMap<Integer, Resource> usagePerPM = new HashMap<Integer, Resource>();

	private PerformanceMonitor() {

	}

	public static PerformanceMonitor getInstance() {
		if (instance == null) {
			instance = new PerformanceMonitor();
		}
		return instance;
	}

	@Override
	public void jobCompleted(Job job) {
		monitor();

	}

	@Override
	public void jobAdded(Job job) {
		monitor();
	}

	private void monitor() {
		// Monitor the Resources of the relevant Cluster-Instance

		// i.e. update all the information
		CloudCluster cluster = JobScheduler.getInstance().getCluster();
		for (PhysicalMachine pm : cluster.getRunningMachines()) {
			usagePerPM.put(pm.getId(), pm.getUsedResources());
		}

	}

	private void analyze() {
		// Analyze the gathered Information

		// Decide if action required, i.e. make a plan
	}

	private void execute() {
		// Try to execute the the plan
		
		/*
		 * TODO: things to consider when migrating a job from one virtualmachine to another:
		 * 		- Job.addCosts(JobScheduler.getInstance().getJobMigrationCost()) needs to be called
		 * 		- the virtual machine that the job was taken from needs to be checked if it has any other jobs
		 * 			- if not, the virtual machine needs to be shut down, furthermore, the physical machine that ran the virtual machine needs to be checked if it runs any other virtual machines
		 * 				- if not, the physicalmachine needs to be shut down
		 * 		things to consider when migrating a virtualmachine from one physicalmachine to another:
		 * 		- Job.addCosts(JobScheduler.getInstance().getVirtualMachineMigrationCost()) needs to be called
		 * 		- the physical machine that ran the virtual machine needs to be checked if it runs any other virtual machines
		 * 			- if not, the physicalmachine needs to be shut down
		 */
	}
}
