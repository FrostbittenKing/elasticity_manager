package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.ArrayList;
import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.LocalCloudClusterFactory;
import at.ac.tuwien.infosys.lsdc.scheduler.heuristics.BestFit;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;

public class JobScheduler {
	public enum PolicyLevel {
		GREEN(0.2, 0.5), 
		GREEN_ORANGE(0.15, 0.6), 
		ORANGE(0.1, 0.7), 
		ORANGE_RED(0.05, 0.8), 
		RED(0.0, 0.9);

		private PolicyLevel(Double overBudget, Double threshHold) {
			this.overBudget = overBudget;
			this.threshHold = threshHold;
		}

		public Double getOverBudget() {
			return overBudget;
		}

		public Double getThreshold() {
			return threshHold;
		}

		private Double overBudget;
		private Double threshHold;
	}

	private static JobScheduler instance = null;

	private CloudCluster cloudCluster = null;
	private PolicyLevel currentPolicyLevel = null;
	private IJobEventListener monitorListener = null;
	private Double jobMigrationCost;
	private Double virtualMachineMigrationCost;
	private Double physicalMachineBootCost;
	

	private JobScheduler() {
	}

	public void initialize(HashMap<Integer, PhysicalMachine> physicalMachines, Double jobMigrationCost, 
			Double virtualMachineMigrationCost, Double physicalMachineBootCost, Double outsourceCosts) {
		this.cloudCluster = LocalCloudClusterFactory.getInstance().createLocalCluster(physicalMachines);
		this.jobMigrationCost = jobMigrationCost;
		this.virtualMachineMigrationCost = virtualMachineMigrationCost;
		this.physicalMachineBootCost = physicalMachineBootCost;
		JobOutsourcer.getInstance().setOutsourceCosts(outsourceCosts);
		currentPolicyLevel = PolicyLevel.GREEN;
	}

	public synchronized void scheduleJob(InsourcedJob job) {
		System.out.println("Scheduled job: " + job + " , WOOHOO!");

		if (cloudCluster.jobFits(job)) {
			job.setMonitorListener(monitorListener);
			job.setCloudListener(cloudCluster);
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

						
						cloudCluster.jobAdded(job);
						monitorListener.jobAdded(job);

					}
				} else {
					/*
					 * TODO: create new virtual machine on running physical
					 * machine --- take policy level into account when creating
					 * new virtual machine --- add job to running virtual
					 * machine
					 */
					cloudCluster.jobAdded(job);
					monitorListener.jobAdded(job);
				}
			} else {
				// TODO: simply add job to virtual machine
				virtualMachine.addJob(job);
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

	private VirtualMachine findVirtualMachine(InsourcedJob job) {
		VirtualMachine[] candidates = cloudCluster.getVirtualHostingCandidates(job);
		if (candidates.length == 0) {
			return null;
		}
		BestFit<VirtualMachine> bestFits = new BestFit<VirtualMachine>(candidates);
		return (VirtualMachine)bestFits.getBestFittingMachine(job);
	}

	private PhysicalMachine findRunningPhysicalMachine(InsourcedJob job) {
		PhysicalMachine[] candidates = cloudCluster.getRunningHostingCandidates(job);
		if (candidates.length == 0) {
			return null;
		}
		
		BestFit<PhysicalMachine> bestFits = new BestFit<PhysicalMachine>(candidates);
		return (PhysicalMachine)bestFits.getBestFittingMachine(job);
	}

	private PhysicalMachine findStoppedPhysicalMachine(InsourcedJob job) {
		PhysicalMachine[] candidates = cloudCluster.getStoppedHostingCandidates(job);
		if (candidates.length == 0) {
			return null;
		}
		BestFit<PhysicalMachine> bestFits = new BestFit<PhysicalMachine>(candidates);
		return (PhysicalMachine)bestFits.getBestFittingMachine(job);
	}

	public static JobScheduler getInstance() {
		if (instance == null) {
			instance = new JobScheduler();
		}
		return instance;
	}

	public ArrayList<PhysicalMachineUsage> getCurrentUsage() {
		return cloudCluster.getUsage();
	}

	public CloudCluster getCluster() {
		return this.cloudCluster;
	}

	public PolicyLevel getCurrentPolicyLevel() {
		return currentPolicyLevel;
	}

	public Double getJobMigrationCost() {
		return jobMigrationCost;
	}

	public Double getVirtualMachineMigrationCost() {
		return virtualMachineMigrationCost;
	}

	public Double getPhysicalMachineBootCost() {
		return physicalMachineBootCost;
	}
}
