package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import at.ac.tuwien.infosys.lsdc.scheduler.IJobEventListener;

public class InsourcedJob extends Job{
	private IJobEventListener cloudListener;
	private IJobEventListener monitorListener;
	private VirtualMachine currentVirtualMachineEnvironment = null;
	private Double migrationCosts;
	
	public InsourcedJob(Integer size, Integer consumedMemory, Integer consumedCPUs,
			Long executionTime) {
		super(size, consumedMemory, consumedCPUs, executionTime);
		this.remainingExecutionTime = executionTime;
		this.migrationCosts = 0.0;
	}

	@Override
	public void run(){
		lastStartTime = System.currentTimeMillis();
		if (remainingExecutionTime > 0){
			try {
				Thread.sleep((long)remainingExecutionTime);
			}
			catch (InterruptedException e) {
				Long executedTime = System.currentTimeMillis() - lastStartTime;
				calculateExecutionCosts(executedTime);
				remainingExecutionTime -= executedTime;
				return;
			}
		}
		calculateExecutionCosts(System.currentTimeMillis() - lastStartTime);

		if (cloudListener != null){
			cloudListener.jobCompleted(this);
		}
		if (monitorListener != null){
			monitorListener.jobCompleted(this);
		}
	}

	@Override
	protected void calculateExecutionCosts(Long executedTime) {
		executionCosts += (executedTime / 1000) * currentVirtualMachineEnvironment.getHost().getPricePerCycle().doubleValue();
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

	public void setExecutionTime(Long executionTime) {
		this.remainingExecutionTime = executionTime;
	}

	public void setCloudListener(IJobEventListener cloudListener) {
		this.cloudListener = cloudListener;
	}

	public void setMonitorListener(IJobEventListener monitorListener) {
		this.monitorListener = monitorListener;
	}

	public void addCosts(Double costs){
		this.migrationCosts += costs;
	}

	public Double getCosts() {
		return migrationCosts;
	}

	public Double getExecutionCosts() {
		return executionCosts;
	}

	@Override
	public String toString() {
		return "Job [size=" + consumedDiskMemory + ", consumedMemory=" + consumedMemory
		+ ", consumedCPUs=" + consumedCPUs + ", executionTime="
		+ remainingExecutionTime + "]";
	}

	public void setCurrentVirtualMachineEnvironment(
			VirtualMachine currentVirtualMachineEnvironment) {
		this.currentVirtualMachineEnvironment = currentVirtualMachineEnvironment;
	}

	public VirtualMachine getCurrentVirtualMachineEnvironment() {
		return currentVirtualMachineEnvironment;
	}
	
	public OutsourcedJob makeOutsourcedJob(){
		return new OutsourcedJob(consumedDiskMemory, consumedMemory, consumedCPUs, remainingExecutionTime);
	}
}
