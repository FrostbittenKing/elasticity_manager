package at.ac.tuwien.infosys.lsdc.scheduler.statistics;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;
import at.ac.tuwien.infosys.lsdc.scheduler.heuristics.LoadMatrix;
import at.ac.tuwien.infosys.lsdc.scheduler.matrix.Matrix;
import at.ac.tuwien.infosys.lsdc.scheduler.matrix.twoDimensional.MatrixHelper;

public class PhysicalMachineUsage {
	private Resource usedResources = null;
	private Resource totalResources = null;
	
	public PhysicalMachineUsage(Resource usedResources, Resource totalResources) {
		this.usedResources = usedResources;
		this.totalResources = totalResources;
	}
	
	public Resource getUsedResources() {
		return usedResources;
	}
	
	public Resource getTotalResources() {
		return totalResources;
	}
	
	public void setUsedResources(Resource usedResources) {
		this.usedResources = usedResources;
	}
	
	/**
	 * calculates the load of the machine in percent
	 * @return double value representing the load
	 */
	public Double getUsageLoad() {
		LoadMatrix currentLoad = new LoadMatrix(3, 1);
		LoadMatrix totalLoad = new LoadMatrix(3, 1);
		currentLoad.addResource(usedResources);
		totalLoad.addResource(totalResources);
		currentLoad.divElement(totalLoad);
		Matrix<Double> relativeLoad = MatrixHelper.calculateRowMean(currentLoad);
		return MatrixHelper.getElement(relativeLoad, 0, 0).doubleValue();
	}
	
	public void setTotalResources(Resource totalResources) {
		this.totalResources = totalResources;
	}	
}
