package at.ac.tuwien.infosys.lsdc.scheduler.monitor;

import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;
import at.ac.tuwien.infosys.lsdc.scheduler.IJobEventListener;
import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.PolicyLevel;
import at.ac.tuwien.infosys.lsdc.scheduler.exception.IllegalValueException;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
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
	public void jobCompleted(InsourcedJob job) {
		free_resources();
		monitor();

	}

	@Override
	public void jobAdded(InsourcedJob job) {
		free_resources();
		monitor();
	}

	private void monitor() {
		// Monitor the Resources of the relevant Cluster-Instance
		// There are 2 things we want to do:
		// * set a useful level (green - red - orange)
		// : the objective function is minimized energy-costs, thus the level
		// : should indicate how much % of overall energy we are consuming right
		// : now
		// * get the current usage of all the PMs

		CloudCluster cluster = JobScheduler.getInstance().getCluster();

		setPolicyLevel(cluster);

		// Gather all the necessary information
		usagePerPM.clear();
		for (PhysicalMachine pm : cluster.getRunningMachines()) {
			usagePerPM.put(pm.getId(), pm.getUsedResources());
		}

		analyze();

	}

	private void setPolicyLevel(CloudCluster cluster) {
		Integer potEnergySum = 0;
		Integer actEnergySum = 0;

		for (PhysicalMachine pm : cluster.getOfflineMachines()) {
			potEnergySum += pm.getPricePerCycle();
		}

		for (PhysicalMachine pm : cluster.getRunningMachines()) {
			actEnergySum += pm.getPricePerCycle();
		}

		potEnergySum += actEnergySum;

		Double usagePercent = (double) (actEnergySum / potEnergySum);

		try {
			JobScheduler.getInstance().setCurrentPolicyLevel(
					PolicyLevel.getAccordingPolicyLevel(usagePercent));
			System.out.println("SETTING POLICY LEVEL TO: " + PolicyLevel.getAccordingPolicyLevel(usagePercent).toString());
		} catch (IllegalValueException e) {
			e.printStackTrace();
		}
	}

	private void analyze() {
		// Analyze the gathered Information and see if it can be optimized
		// : i.e. find a better job/VM/PM-assignment
	
		// Decide if action required, i.e. make a plan
		// we are going to implement a vnd-variant:
		// * until stopping condition is met
		// *   iterate through neighborhoods
		// *     (generate (random) solution y by applying change-op)
		// *     find local minimum of y: y'
		// *     if y' better than initial solution, assign as new starting point and reset neighborhood-cnt
		// *     else neighborhood++
		// *   until neighborhood_max
		// * end
	}

	private void execute() {
		// Try to execute the the plan

		/*
		 * TODO: things to consider when migrating a job from one virtualmachine
		 * to another: -
		 * Job.addCosts(JobScheduler.getInstance().getJobMigrationCost()) needs
		 * to be called - the virtual machine that the job was taken from needs
		 * to be checked if it has any other jobs - if not, the virtual machine
		 * needs to be shut down, furthermore, the physical machine that ran the
		 * virtual machine needs to be checked if it runs any other virtual
		 * machines - if not, the physicalmachine needs to be shut down things
		 * to consider when migrating a virtualmachine from one physicalmachine
		 * to another: -
		 * Job.addCosts(JobScheduler.getInstance().getVirtualMachineMigrationCost
		 * ()) needs to be called - the physical machine that ran the virtual
		 * machine needs to be checked if it runs any other virtual machines -
		 * if not, the physicalmachine needs to be shut down
		 */
	}
	
	private void free_resources() {
		
	}
}
