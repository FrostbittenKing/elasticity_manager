package at.ac.tuwien.infosys.lsdc.scheduler.statistics;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;

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
	
	public void setTotalResources(Resource totalResources) {
		this.totalResources = totalResources;
	}	
}
