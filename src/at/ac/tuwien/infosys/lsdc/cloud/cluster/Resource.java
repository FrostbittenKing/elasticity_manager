package at.ac.tuwien.infosys.lsdc.cloud.cluster;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;

public class Resource {
	private Integer[] resources;
	private Integer id;
	
	public Resource(Integer id, Integer cpus, Integer memory, Integer diskMemory) {
		this.resources = new Integer[] {cpus, memory, diskMemory};
		this.id = id;
	}
	
	public Double[] getResources() {
		return new Double[] {resources[ResourceType.CPU()].doubleValue(), resources[ResourceType.MEMORY()].doubleValue(), 
				resources[ResourceType.DISKMEMORY()].doubleValue()};
	}

	public Integer getId() {
		return id;
	}
	
	public void addJob(InsourcedJob job) {
		resources[ResourceType.CPU()] += job.getConsumedCPUs();
		resources[ResourceType.MEMORY()] += job.getConsumedMemory();
		resources[ResourceType.DISKMEMORY()] += job.getConsumedDiskMemory();
	}

	public enum ResourceType {
		CPU(0),
		MEMORY(1),
		DISKMEMORY(2);
		
		ResourceType(Integer type) {
			
		}
		
		static Integer CPU() {
			return 0;
		}
		
		static Integer MEMORY() {
			return 1;
		}
		
		static Integer DISKMEMORY() {
			return 2;
		}
	}
	
	
}
