package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.ArrayList;
import java.util.HashMap;

import sun.misc.PerformanceLogger;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.LocalCloudClusterFactory;
import at.ac.tuwien.infosys.lsdc.scheduler.exception.IllegalValueException;
import at.ac.tuwien.infosys.lsdc.scheduler.heuristics.BestFit;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.PerformanceMonitor;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;

public class JobScheduler {
	public enum PolicyLevel {
		GREEN(1.2, 0.5), 
		GREEN_ORANGE(1.15, 0.6), 
		ORANGE(1.1, 0.7), 
		ORANGE_RED(1.05, 0.8), 
		RED(1.0, 0.9);


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

	public void initialize(HashMap<Integer, PhysicalMachine> physicalMachines,
			Double jobMigrationCost, Double virtualMachineMigrationCost,
			Double physicalMachineBootCost, Double outsourceCosts) {
		this.cloudCluster = LocalCloudClusterFactory.getInstance()
				.createLocalCluster(physicalMachines);
		this.jobMigrationCost = jobMigrationCost;
		this.virtualMachineMigrationCost = virtualMachineMigrationCost;
		this.physicalMachineBootCost = physicalMachineBootCost;
		JobOutsourcer.getInstance().setOutsourceCosts(outsourceCosts);
		currentPolicyLevel = PolicyLevel.GREEN;
		monitorListener = PerformanceMonitor.getInstance();
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
						// create modifed job template to start a physical machine taking the policy level into account
						InsourcedJob policyAwareJobCosts = job.modifyCosts(currentPolicyLevel.getOverBudget());
						// start the matching physical machine, and create a policylevel-aware VM machine
						// return the VM
						PhysicalMachine newRunningMachine = cloudCluster.startMachine(stoppedPhysicalMachine); 
						VirtualMachine startedVM = newRunningMachine.startVirtualMachine(
								policyAwareJobCosts.getConsumedDiskMemory(), 
								policyAwareJobCosts.getConsumedMemory(), 
								policyAwareJobCosts.getConsumedCPUs());
						//add the job to the vm
						startedVM.addJob(job);
						cloudCluster.jobAdded(job);
						monitorListener.jobAdded(job);
					}
				} else {
					InsourcedJob policyAwareJobCosts = job.modifyCosts(currentPolicyLevel.getOverBudget());
					VirtualMachine startedMachine = runningPhysicalMachine.startVirtualMachine(
							policyAwareJobCosts.getConsumedDiskMemory(),
							policyAwareJobCosts.getConsumedMemory(),
							policyAwareJobCosts.getConsumedCPUs());
					startedMachine.addJob(job);
					cloudCluster.jobAdded(job);
					monitorListener.jobAdded(job);
				}
			} else {
				virtualMachine.addJob(job);
			}

		} else {
			// TODO: job doesn't fit, outsource to other cloud
		}
	}

	private VirtualMachine findVirtualMachine(InsourcedJob job) {

		InsourcedJob policyAwareJobRequirements = job.modifyCosts(currentPolicyLevel.getOverBudget());
		VirtualMachine[] candidates = cloudCluster.getVirtualHostingCandidates(policyAwareJobRequirements);
		if (candidates.length == 0) {
			return null;
		}
		BestFit<VirtualMachine> bestFits = new BestFit<VirtualMachine>(candidates);
		return (VirtualMachine)bestFits.getBestFittingMachine(policyAwareJobRequirements);
	}

	private PhysicalMachine findRunningPhysicalMachine(InsourcedJob job) {
		InsourcedJob policyAwareJobRequirements = job.modifyCosts(currentPolicyLevel.getOverBudget());
		PhysicalMachine[] candidates = cloudCluster.getRunningHostingCandidates(policyAwareJobRequirements);

		if (candidates.length == 0) {
			return null;
		}

		BestFit<PhysicalMachine> bestFits = new BestFit<PhysicalMachine>(candidates);
		return (PhysicalMachine)bestFits.getBestFittingMachine(policyAwareJobRequirements);
	}

	private PhysicalMachine findStoppedPhysicalMachine(InsourcedJob job) {
		InsourcedJob policyAwareJobRequirements = job.modifyCosts(currentPolicyLevel.getOverBudget());
		PhysicalMachine[] candidates = cloudCluster.getStoppedHostingCandidates(policyAwareJobRequirements);

		if (candidates.length == 0) {
			return null;
		}
		BestFit<PhysicalMachine> bestFits = new BestFit<PhysicalMachine>(candidates);
		return (PhysicalMachine)bestFits.getBestFittingMachine(policyAwareJobRequirements);

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

	public void setCurrentPolicyLevel(PolicyLevel currentPolicyLevel) {
		this.currentPolicyLevel = currentPolicyLevel;
	}

	public static PolicyLevel getAccordingPolicyLevel(Double percent)
			throws IllegalValueException {
		if (percent > 1.0 || percent < 0.0) {
			throw new IllegalValueException("Value must be below 1.0");
		}

		if (percent <= PolicyLevel.GREEN.getThreshold())
			return PolicyLevel.GREEN;
		else if (percent > PolicyLevel.RED.getThreshold())
			return PolicyLevel.RED;

		PolicyLevel result = PolicyLevel.GREEN;

		for (PolicyLevel candidate : PolicyLevel.values()) {
			if (percent <= candidate.getThreshold()) {
				result = candidate;
				break;
			}
		}

		return result;
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
