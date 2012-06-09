package at.ac.tuwien.infosys.lsdc.cloud.cluster.bus.messages;

public class ResourceRequest {
	
	private Integer diskMemory = null;
	private Integer memory = null;
	private Integer cpus = null;
	public Integer getDiskMemory() {
		return diskMemory;
	}
	public void setDiskMemory(Integer diskMemory) {
		this.diskMemory = diskMemory;
	}
	public Integer getMemory() {
		return memory;
	}
	public void setMemory(Integer memory) {
		this.memory = memory;
	}
	public Integer getCpus() {
		return cpus;
	}
	public void setCpus(Integer cpus) {
		this.cpus = cpus;
	}
	public ResourceRequest(Integer diskMemory, Integer memory, Integer cpus) {
		super();
		this.diskMemory = diskMemory;
		this.memory = memory;
		this.cpus = cpus;
	}
	
	
}
