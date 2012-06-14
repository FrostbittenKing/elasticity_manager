package at.ac.tuwien.infosys.lsdc.scheduler.monitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;
import at.ac.tuwien.infosys.lsdc.scheduler.IJobEventListener;
import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.PolicyLevel;
import at.ac.tuwien.infosys.lsdc.scheduler.exception.IllegalValueException;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.IOperation;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.OperationFactory;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.OperationType;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.SolutionReducer;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.exception.OperationNotSupportedException;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.IOperationStep;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.exception.StepNotReproducableException;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public class PerformanceMonitor implements IJobEventListener {
	
	private static PerformanceMonitor instance;

	private HashMap<Integer, Resource> usagePerPM = new HashMap<Integer, Resource>();
	private CloudCluster cluster = null;

	private PerformanceMonitor() {

	}

	public static PerformanceMonitor getInstance() {
		if (instance == null) {
			instance = new PerformanceMonitor();
		}
		return instance;
	}

	public void initialize(CloudCluster cluster){
		this.cluster = cluster;
	}

	@Override
	public void jobCompleted(InsourcedJob job) {
		synchronized(cluster){
			free_resources();
			monitor();
		}

	}

	@Override
	public void jobAdded(InsourcedJob job) {
		synchronized(cluster){
			//		free_resources();
			monitor();
		}
	}

	private void monitor() {
		// Monitor the Resources of the relevant Cluster-Instance
		// There are 2 things we want to do:
		// * set a useful level (green - red - orange)
		// : the objective function is minimized energy-costs, thus the level
		// : should indicate how much % of overall energy we are consuming right
		// : now

		setPolicyLevel();

		// Gather all the necessary information
		// * get the current usage of all the PMs
		usagePerPM.clear();
		for (PhysicalMachine pm : cluster.getRunningMachines()) {
			usagePerPM.put(pm.getId(), pm.getUsedResources());
		}
		analyze();

	}

	private void setPolicyLevel() {
		Integer potEnergySum = 0;
		Integer actEnergySum = 0;

		for (PhysicalMachine pm : cluster.getOfflineMachines()) {
			potEnergySum += pm.getPricePerCycle();
		}

		for (PhysicalMachine pm : cluster.getRunningMachines()) {
			actEnergySum += pm.getPricePerCycle();
		}

		potEnergySum += actEnergySum;

		Double usagePercent = (double) actEnergySum / (double) potEnergySum;

		try {
			JobScheduler.getInstance().setCurrentPolicyLevel(
					PolicyLevel.getAccordingPolicyLevel(usagePercent));
			System.out.println("SETTING POLICY LEVEL TO: " + PolicyLevel.getAccordingPolicyLevel(usagePercent).toString());
		} catch (IllegalValueException e) {
			e.printStackTrace();
		}
	}

	private void analyze() {
		Assignment currentState = new Assignment(cluster.getRunningMachines(), cluster.getOfflineMachines());
		
		ArrayList<Change> finalSolutionList = new ArrayList<Change>();
		ArrayList<Change> moveJobsolutions = new ArrayList<Change>();
		ArrayList<Change> moveVMsolutions = new ArrayList<Change>();
		
		IOperation redistributeJobOperation = null;
		IOperation redistributeVMOperation = null;
		
		try {
			redistributeJobOperation = OperationFactory.getInstance().getOperation(OperationType.Redistribute_VM);
			redistributeVMOperation = OperationFactory.getInstance().getOperation(OperationType.Redistribute_PM);
		} catch (OperationNotSupportedException e) {
			e.printStackTrace();
			return;
		}
		
		
		moveJobsolutions.addAll(redistributeJobOperation.execute(currentState));
		
		for (Change currentSolution : moveJobsolutions){
			moveVMsolutions.addAll(redistributeVMOperation.execute(currentSolution.getDestination()));
		}
		
		finalSolutionList.addAll(moveJobsolutions);
		finalSolutionList.addAll(moveVMsolutions);
		
		Collections.sort(finalSolutionList);
		if (finalSolutionList.size() > 0) {
			execute(finalSolutionList.get(0));
		}
	}

	private void execute(Change plan) {
		for (IOperationStep step : plan.getSteps()) {
			try {
				step.execute(plan.getSource());
			} catch (StepNotReproducableException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	private void free_resources() {
		SolutionReducer.reduce(cluster);
	}
}
