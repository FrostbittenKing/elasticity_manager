package at.ac.tuwien.infosys.lsdc.scheduler.heuristics;

import at.ac.tuwien.infosys.lsdc.scheduler.matrix.Matrix;
import at.ac.tuwien.infosys.lsdc.scheduler.matrix.twoDimensional.MatrixHelper;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public class BestFit {
	
	private static final Integer NUMBER_OF_ATTRIBUTES = 3;
	
	PhysicalMachine[] machines = null;
	
	public BestFit(PhysicalMachine[] machines) {
		this.machines = machines;
	}
	
	public void setRunningMachines(PhysicalMachine[] machines) {
		this.machines = machines;
	}
	
	/**
	 * Calculates the machine, in which the job passed by the argument job
	 * fits in best
	 * @param job the job to be scheduled in a machine
	 * @return the id of the machine
	 */
	public Integer getBestFittingMachine(Job job) {
		
		//TODO
		// some checks if the machine has enough available resources to host the job
		// if not, exclude the machine from further calculations
		// also return null or something if no machine can host the job
		// still to be determined if this check is necessary, or if getBestFittingMachine
		// always returns a solution (guaranteed that a machine that can host the job exists??)
		
		int nrOfMachines = machines.length;
		Resource [] currentUsedResources = new Resource[nrOfMachines];
		Resource [] totalResources = new Resource[nrOfMachines];
		PMLoadMatrix loadMatrix = new PMLoadMatrix(NUMBER_OF_ATTRIBUTES, nrOfMachines);
		PMLoadMatrix allAvailable = new PMLoadMatrix(NUMBER_OF_ATTRIBUTES, nrOfMachines);
		
		int machinecount = 0;
		Resource currentResource;
		for (PhysicalMachine currentMachine : machines) {
			currentResource = currentMachine.getUsedResources();
			currentResource.addJob(job);
			currentUsedResources[machinecount] = currentResource;
			totalResources[machinecount] = currentMachine.getTotalResources();
			
			loadMatrix.addResource(currentResource);
			allAvailable.addResource(totalResources[machinecount]);
			machinecount++;
		}
		loadMatrix.divElement(allAvailable);
		Matrix<Double> rowMeans = MatrixHelper.calculateRowMean(loadMatrix);
	
		if (nrOfMachines == 1)
			return totalResources[0].getId();
		
		//Integer currentBestMachine = totalResources[0].getId();
		Integer currentBestMachinePosition = 0;
		Double currentBestFitLoad = MatrixHelper.getElement(rowMeans,0, 0).doubleValue();
		for (int i = 1; i < nrOfMachines; i++)
		{
			Double currentLoadValue = MatrixHelper.getElement(rowMeans, i, 0).doubleValue();
			//currentBestFitLoad = MatrixHelper.getElement(rowMeans,0, currentBestMachinePosition).doubleValue();
			if (currentBestFitLoad < currentLoadValue) {
				currentBestFitLoad = currentLoadValue;
				currentBestMachinePosition = i;
			}
		}
		
		return totalResources[currentBestMachinePosition].getId();
	}
}
