package at.ac.tuwien.infosys.lsdc.scheduler.objects;

public abstract class Job implements Runnable{
	protected Integer consumedDiskMemory = null;
	protected Integer consumedMemory = null;
	protected Integer consumedCPUs = null;
	protected Long remainingExecutionTime = null;
	protected Long lastStartTime;
	
	protected Job(){
		
	}
	
	protected Job(Integer size, Integer consumedMemory, Integer consumedCPUs,
			Long executionTime) {
		super();
		this.consumedDiskMemory = size;
		this.consumedMemory = consumedMemory;
		this.consumedCPUs = consumedCPUs;
		this.remainingExecutionTime = executionTime;
	}
	
	public Integer getConsumedDiskMemory() {
		return consumedDiskMemory;
	}

	public Integer getConsumedMemory() {
		return consumedMemory;
	}

	public Integer getConsumedCPUs() {
		return consumedCPUs;
	}

	public Long getExecutionTime() {
		return remainingExecutionTime;
	}

	public void setConsumedDiskMemory(Integer size) {
		this.consumedDiskMemory = size;
	}

	public void setConsumedMemory(Integer consumedMemory) {
		this.consumedMemory = consumedMemory;
	}

	public void setConsumedCPUs(Integer consumedCPUs) {
		this.consumedCPUs = consumedCPUs;
	}

	public void setRemainingExecutionTime(Long executionTime) {
		this.remainingExecutionTime = executionTime;
	}

	public Long getLastStartTime() {
		return lastStartTime;
	}

	public void setLastStartTime(Long lastStartTime) {
		this.lastStartTime = lastStartTime;
	}
}
