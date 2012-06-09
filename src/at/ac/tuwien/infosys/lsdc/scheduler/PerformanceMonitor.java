package at.ac.tuwien.infosys.lsdc.scheduler;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler.PolicyLevel;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;

public class PerformanceMonitor implements IJobEventListener {

	private static PerformanceMonitor instance;
	private PolicyLevel policyLevel;

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
		// TODO Auto-generated method stub

	}

	@Override
	public void jobAdded(Job job) {
		// TODO Auto-generated method stub

	}
}
