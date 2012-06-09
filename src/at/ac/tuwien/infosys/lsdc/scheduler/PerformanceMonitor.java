package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public class PerformanceMonitor implements IJobEventListener {

	private static PerformanceMonitor instance;

	private HashMap<Double, Double> usagePerPM;

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

		}

	}

	private void analyze() {
		// Analyze the gathered Information

		// Decide if action required, i.e. make a plan
	}

	private void execute() {
		// Try to execute the the plan
	}
}
