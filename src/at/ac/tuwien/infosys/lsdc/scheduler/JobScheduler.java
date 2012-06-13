package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.ArrayList;
import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.LocalCloudClusterFactory;
import at.ac.tuwien.infosys.lsdc.scheduler.heuristics.BestFit;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.PerformanceMonitor;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;

public class JobScheduler {
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
			System.out.println("Enough resources, trying to find slot...");
			findFittingJobSlot(job);
		}
		else {
			JobOutsourcer.getInstance().outSourceJob(job.makeOutsourcedJob());
		}
	}

	private void findFittingJobSlot(InsourcedJob job) {
		job.setMonitorListener(monitorListener);
		job.setCloudListener(cloudCluster);
		VirtualMachine virtualMachine = findVirtualMachine(job);
		if (virtualMachine == null) {
			System.out.println("Could not find existing VM, searching for running PM for job...");
			findRunningPhysicalMachineForJob(job);
		} 
		else {
			System.out.println("Found existing VM for job, adding to VM.");
			addJobToVirtualMachine(job, virtualMachine);
		}
	}

	private void findRunningPhysicalMachineForJob(InsourcedJob job) {
		PhysicalMachine runningPhysicalMachine = findRunningPhysicalMachine(job);
		if (runningPhysicalMachine == null) {
			System.out.println("Could not find running PM, searching for stopped PM for job...");
			findStoppedPhysicalMachineForJob(job);
		} 
		else {
			System.out.println("Found running PM for job, adding to job to new VM.");
			addJobToPhysicalMachine(job, runningPhysicalMachine);
		}
	}

	private void findStoppedPhysicalMachineForJob(InsourcedJob job) {
		PhysicalMachine stoppedPhysicalMachine = findStoppedPhysicalMachine(job);
		if (stoppedPhysicalMachine == null) {
			System.out.println("Could not find stopped PM, outsourcing...");
			JobOutsourcer.getInstance().outSourceJob(job.makeOutsourcedJob());
		} 
		else {
			System.out.println("Found stopped PM for job, starting PM, creating new VM...");
			startMachineForJob(job, stoppedPhysicalMachine);
		}
	}

	private void startMachineForJob(InsourcedJob job,
			PhysicalMachine stoppedPhysicalMachine) {
		// create modifed job template to start a physical machine taking the policy level into account
		InsourcedJob policyAwareJobCosts = job.modifyCosts(currentPolicyLevel.getOverBudget());
		// start the matching physical machine, and create a policylevel-aware VM machine
		// return the VM
		PhysicalMachine newRunningMachine = cloudCluster.startMachine(stoppedPhysicalMachine); 
		VirtualMachine startedVM = newRunningMachine.startVirtualMachine(
				policyAwareJobCosts.getConsumedDiskMemory(), 
				policyAwareJobCosts.getConsumedMemory(), 
				policyAwareJobCosts.getConsumedCPUs());
		addJobToVirtualMachine(job, startedVM);
	}

	private void addJobToPhysicalMachine(InsourcedJob job,
			PhysicalMachine runningPhysicalMachine) {
		InsourcedJob policyAwareJobCosts = job.modifyCosts(currentPolicyLevel.getOverBudget());
		VirtualMachine startedMachine = runningPhysicalMachine.startVirtualMachine(
				policyAwareJobCosts.getConsumedDiskMemory(),
				policyAwareJobCosts.getConsumedMemory(),
				policyAwareJobCosts.getConsumedCPUs());
		addJobToVirtualMachine(job, startedMachine);
	}

	private void addJobToVirtualMachine(InsourcedJob job,
			VirtualMachine virtualMachine) {
		virtualMachine.addJob(job);
		cloudCluster.jobAdded(job);
		monitorListener.jobAdded(job);
	}

	private synchronized VirtualMachine findVirtualMachine(InsourcedJob job) {
		InsourcedJob policyAwareJobRequirements = job.modifyCosts(currentPolicyLevel.getOverBudget());
		VirtualMachine[] candidates = cloudCluster.getVirtualHostingCandidates(policyAwareJobRequirements);
		if (candidates.length == 0) {
			return null;
		}
		BestFit<VirtualMachine> bestFits = new BestFit<VirtualMachine>(candidates);
		return (VirtualMachine)bestFits.getBestFittingMachine(policyAwareJobRequirements);
	}

	private synchronized PhysicalMachine findRunningPhysicalMachine(InsourcedJob job) {
		InsourcedJob policyAwareJobRequirements = job.modifyCosts(currentPolicyLevel.getOverBudget());
		PhysicalMachine[] candidates = cloudCluster.getRunningHostingCandidates(policyAwareJobRequirements);

		if (candidates.length == 0) {
			return null;
		}

		BestFit<PhysicalMachine> bestFits = new BestFit<PhysicalMachine>(candidates);
		return (PhysicalMachine)bestFits.getBestFittingMachine(policyAwareJobRequirements);
	}

	private synchronized PhysicalMachine findStoppedPhysicalMachine(InsourcedJob job) {
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

	public synchronized ArrayList<PhysicalMachineUsage> getCurrentUsage() {
		return cloudCluster.getUsage();
	}

	public CloudCluster getCluster() {
		return this.cloudCluster;
	}

	public synchronized PolicyLevel getCurrentPolicyLevel() {
		return currentPolicyLevel;
	}

	public synchronized void setCurrentPolicyLevel(PolicyLevel currentPolicyLevel) {
		this.currentPolicyLevel = currentPolicyLevel;
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
