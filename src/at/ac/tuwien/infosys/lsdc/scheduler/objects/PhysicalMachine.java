package at.ac.tuwien.infosys.lsdc.scheduler.objects;

public class PhysicalMachine {
	private Integer CPUs = null;
	private Integer memory = null;
	private Integer diskMemory = null;
	private Integer pricePerCycle = null;
	private transient Boolean isRunning = false;
	
	public Integer getPricePerCycle() {
		return pricePerCycle;
	}
	public Boolean isRunning() {
		return isRunning;
	}
	public void setPricePerCycle(Integer pricePerCycle) {
		this.pricePerCycle = pricePerCycle;
	}
	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}	
	
	public Integer getCPUs() {
		return CPUs;
	}
	public Integer getMemory() {
		return memory;
	}
	public Integer getDiskMemory() {
		return diskMemory;
	}
	public void setCPUs(Integer cPUs) {
		CPUs = cPUs;
	}
	public void setMemory(Integer memory) {
		this.memory = memory;
	}
	public void setDiskMemory(Integer diskMemory) {
		this.diskMemory = diskMemory;
	}
	@Override
	public String toString() {
		return "PhysicalMachine [CPUs=" + CPUs + ", memory=" + memory
				+ ", diskMemory=" + diskMemory + ", pricePerCycle="
				+ pricePerCycle + ", isRunning=" + isRunning + "]";
	}
}
