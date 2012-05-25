package at.ac.tuwien.infosys.lsdc.scheduler.objects;

public class Job implements Runnable{
	private Integer size = null;
	private Integer consumedMemory = null;
	private Integer consumedCPUs = null;
	private Integer executionTime = null;
//	private Integer priority = null;
	
	//TODO: add callback interface field, implement priority
	
	public Job(Integer size, Integer consumedMemory, Integer consumedCPUs,
			Integer executionTime) {
		super();
		this.size = size;
		this.consumedMemory = consumedMemory;
		this.consumedCPUs = consumedCPUs;
		this.executionTime = executionTime;
	}
	
	@Override
	public void run(){
		try {
			Thread.sleep((long)executionTime);
		}
		catch (InterruptedException e) {
			//TODO if thread sleep is interrupted, we need to throw some kind of error and abort simulation
			e.printStackTrace();
		}
		//TODO add callback call when job is complete
		
	}

	public Integer getSize() {
		return size;
	}

	public Integer getConsumedMemory() {
		return consumedMemory;
	}

	public Integer getConsumedCPUs() {
		return consumedCPUs;
	}

	public Integer getExecutionTime() {
		return executionTime;
	}

//	public Integer getPriority() {
//		return priority;
//	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setConsumedMemory(Integer consumedMemory) {
		this.consumedMemory = consumedMemory;
	}

	public void setConsumedCPUs(Integer consumedCPUs) {
		this.consumedCPUs = consumedCPUs;
	}

	public void setExecutionTime(Integer executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public String toString() {
		return "Job [size=" + size + ", consumedMemory=" + consumedMemory
				+ ", consumedCPUs=" + consumedCPUs + ", executionTime="
				+ executionTime + "]";
	}
	
//	public void setPriority(Integer priority) {
//		this.priority = priority;
//	}
	
	
}
