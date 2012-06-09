package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.ICloudClusterManager;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;

public class JobScheduler {
	public enum PolicyLevel {
		GREEN(0.2), GREEN_ORANGE(0.15), ORANGE(0.1), ORANGE_RED(0.05), RED(0.0);

		private PolicyLevel(Double overBudget) {
			this.overBudget = overBudget;
		}

		public Double getOverBudget() {
			return overBudget;
		}

		private Double overBudget;
	}

	private static JobScheduler instance = null;

	private ICloudClusterManager cloudCluster = null;
	private PolicyLevel currentPolicyLevel;
	private IJobEventListener perfMonitor;

	private JobScheduler() {

	}

	public void initialize(ICloudClusterManager cloudCluster) {
		this.cloudCluster = cloudCluster;
		this.perfMonitor = PerformanceMonitor.getInstance();
	}

	public synchronized void scheduleJob(Job job) {
		System.out.println("Scheduled job: " + job + " , WOOHOO!");

		if (cloudCluster.jobFits(job)) {
			VirtualMachine virtualMachine = findVirtualMachine(job);
			if (virtualMachine == null) {
				PhysicalMachine runningPhysicalMachine = findRunningPhysicalMachine(job);
				if (runningPhysicalMachine == null) {
					PhysicalMachine stoppedPhysicalMachine = findStoppedPhysicalMachine(job);
					if (stoppedPhysicalMachine == null) {
						/*
						 * TODO: can't find fit, we are fucked, outsource or
						 * queue job
						 */
					} else {
						/*
						 * TODO: start physical machine create virtual machine
						 * --- take policy level into account when creating new
						 * virtual machine --- add job new virtual machine
						 */
						perfMonitor.jobAdded(null);
					}
				} else {
					/*
					 * TODO: create new virtual machine on running physical
					 * machine --- take policy level into account when creating
					 * new virtual machine --- add job to running virtual
					 * machine
					 */
					perfMonitor.jobAdded(null);
				}
			} else {
				// TODO: simply add job to virtual machine
			}

		} else {
			// TODO: job doesn't fit, outsource to other cloud
		}
		/*
		 * TODO: check if there is enough memory, diskmemory, cpus to fit new
		 * job if yes, do fancy scheduling shit with bin packing if physical
		 * machine needs to be started, add its cost per cycle to
		 * currentCycleCosts if a physical machine is shut down, remove the
		 * costs if not, outsource job to other cloud finally, if job was able
		 * to be scheduled locally, add job diskmemory, memory, cpu requirements
		 * to currentusage, then call VirtualMachine.addJob(job)
		 */
	}

	private VirtualMachine findVirtualMachine(Job job) {
		/*
		 * TODO: this method needs to find the BEST FITTING virtual machine for
		 * the job
		 */
		return null;
	}

	private PhysicalMachine findRunningPhysicalMachine(Job job) {
		// TODO:
		// TODO: this method needs to find the BEST FITTING running physical
		// machine for the job
		return null;
	}

	private PhysicalMachine findStoppedPhysicalMachine(Job job) {
		/*
		 * TODO this method needs to find the BEST FITTING stopped physical
		 * machine for the job in this case, best fitting means the physical
		 * machine
		 */
		return null;
	}

	public static JobScheduler getInstance() {
		if (instance == null) {
			instance = new JobScheduler();
		}
		return instance;
	}

	public void jobCompleted(Job job) {
		cloudCluster.jobCompleted(job);
	}

	public ArrayList<PhysicalMachineUsage> getCurrentUsage() {
		return cloudCluster.getUsage();
	}
}
