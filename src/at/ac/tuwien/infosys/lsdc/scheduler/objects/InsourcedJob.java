package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import at.ac.tuwien.infosys.lsdc.scheduler.IJobEventListener;

public class InsourcedJob extends Job implements Cloneable{
	private IJobEventListener cloudListener;
	private IJobEventListener monitorListener;
	private VirtualMachine currentVirtualMachineEnvironment = null;
	
	public InsourcedJob(){
		super();
	}
	
	public InsourcedJob(Integer size, Integer consumedMemory, Integer consumedCPUs,
			Long executionTime) {
		super(size, consumedMemory, consumedCPUs, executionTime);
		this.remainingExecutionTime = executionTime;
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
				remainingExecutionTime -= executedTime;
				return;
			}
		}
		
		if (cloudListener != null){
			cloudListener.jobCompleted(this);
		}
		if (monitorListener != null){
			monitorListener.jobCompleted(this);
		}
		
		System.out.println("Insourced job finished: " + this.toString());
		System.out.println("Removed myself from VM, remaining jobs running in VM: " + currentVirtualMachineEnvironment.getRunningJobs().size());
	}

	public void setCloudListener(IJobEventListener cloudListener) {
		this.cloudListener = cloudListener;
	}

	public void setMonitorListener(IJobEventListener monitorListener) {
		this.monitorListener = monitorListener;
	}
	
	
	
	public void setCurrentVirtualMachineEnvironment(
			VirtualMachine currentVirtualMachineEnvironment) {
		this.currentVirtualMachineEnvironment = currentVirtualMachineEnvironment;
	}

	public VirtualMachine getCurrentVirtualMachineEnvironment() {
		return currentVirtualMachineEnvironment;
	}
	
	public InsourcedJob modifyCosts(Double multiplicator) {
		return new InsourcedJob(
				new Double(Math.ceil(this.consumedDiskMemory * multiplicator)).intValue(), 
				new Double(Math.ceil(this.consumedMemory * multiplicator)).intValue(), 
				new Double(Math.ceil(this.consumedCPUs * multiplicator)).intValue(), remainingExecutionTime);
	}
	
	public OutsourcedJob makeOutsourcedJob(){
		return new OutsourcedJob(consumedDiskMemory, consumedMemory, consumedCPUs, remainingExecutionTime);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		InsourcedJob clonedJob = new InsourcedJob();
		
		clonedJob.setConsumedCPUs(new Integer(consumedCPUs));
		clonedJob.setConsumedDiskMemory(new Integer(consumedDiskMemory));
		clonedJob.setConsumedMemory(new Integer(consumedMemory));
		
		clonedJob.setRemainingExecutionTime(remainingExecutionTime);
		clonedJob.setLastStartTime(lastStartTime);	
		
		return super.clone();
	}

	@Override
	public String toString() {
		return "InsourcedJob [currentVirtualMachineEnvironment="
				+ currentVirtualMachineEnvironment + ", consumedDiskMemory="
				+ consumedDiskMemory + ", consumedMemory=" + consumedMemory
				+ ", consumedCPUs=" + consumedCPUs
				+ ", remainingExecutionTime=" + remainingExecutionTime
				+ ", lastStartTime=" + lastStartTime + "]";
	}	
}
