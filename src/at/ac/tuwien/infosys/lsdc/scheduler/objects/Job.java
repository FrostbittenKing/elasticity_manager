package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.IJobEventListener;

public class Job implements Runnable{
	private Integer consumedDiskMemory = null;
	private Integer consumedMemory = null;
	private Integer consumedCPUs = null;
	private Integer executionTime = null;
//	private Integer priority = null;
	private IJobEventListener cloudListener;
	private IJobEventListener monitorListener;
	private Double costs;
	
	//TODO:  implement priority
	
	public Job(Integer size, Integer consumedMemory, Integer consumedCPUs,
			Integer executionTime) {
		super();
		this.consumedDiskMemory = size;
		this.consumedMemory = consumedMemory;
		this.consumedCPUs = consumedCPUs;
		this.executionTime = executionTime;
		this.costs = 0.0;
	}
	
	@Override
	public void run(){
		try {
			Thread.sleep((long)executionTime);
		}
		catch (InterruptedException e) {
			//TODO if thread sleep is interrupted, we might need to throw some kind of error and abort simulation
			e.printStackTrace();
		}
		
		cloudListener.jobCompleted(this);
		monitorListener.jobCompleted(this);
					
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

	public Integer getExecutionTime() {
		return executionTime;
	}

//	public Integer getPriority() {
//		return priority;
//	}

	public void setConsumedDiskMemory(Integer size) {
		this.consumedDiskMemory = size;
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

	public void setCloudListener(IJobEventListener cloudListener) {
		this.cloudListener = cloudListener;
	}

	public void setMonitorListener(IJobEventListener monitorListener) {
		this.monitorListener = monitorListener;
	}
	
	public void addCosts(Double costs){
		this.costs += costs;
	}

	public Double getCosts() {
		return costs;
	}

	@Override
	public String toString() {
		return "Job [size=" + consumedDiskMemory + ", consumedMemory=" + consumedMemory
				+ ", consumedCPUs=" + consumedCPUs + ", executionTime="
				+ executionTime + "]";
	}	
}
