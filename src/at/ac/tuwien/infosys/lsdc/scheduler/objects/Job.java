package at.ac.tuwien.infosys.lsdc.scheduler.objects;

public abstract class Job implements Runnable{
	protected Integer consumedDiskMemory = null;
	protected Integer consumedMemory = null;
	protected Integer consumedCPUs = null;
	protected Long remainingExecutionTime = null;
	protected Double executionCosts;
	protected Long lastStartTime;
	
	protected Job(Integer size, Integer consumedMemory, Integer consumedCPUs,
			Long executionTime) {
		super();
		this.consumedDiskMemory = size;
		this.consumedMemory = consumedMemory;
		this.consumedCPUs = consumedCPUs;
		this.remainingExecutionTime = executionTime;
		this.executionCosts = 0.0;
	}

	protected abstract void calculateExecutionCosts(Long executedTime);

}
