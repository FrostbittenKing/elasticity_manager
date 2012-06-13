package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import at.ac.tuwien.infosys.lsdc.scheduler.IOutsourcedJobCompletionListener;

public class OutsourcedJob extends Job{
	private IOutsourcedJobCompletionListener listener = null;

	protected OutsourcedJob(Integer size, Integer consumedMemory,
			Integer consumedCPUs, Long executionTime) {
		super(size, consumedMemory, consumedCPUs, executionTime);
	}

	@Override
	public void run() {
		try {
			Thread.sleep((long)remainingExecutionTime);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		listener.jobCompleted(this);
	}
	
	public void setListener(IOutsourcedJobCompletionListener listener) {
		this.listener = listener;
	}

	@Override
	public String toString() {
		return "OutsourcedJob [listener=" + listener + ", consumedDiskMemory="
				+ consumedDiskMemory + ", consumedMemory=" + consumedMemory
				+ ", consumedCPUs=" + consumedCPUs
				+ ", remainingExecutionTime=" + remainingExecutionTime
				+ ", lastStartTime=" + lastStartTime + "]";
	}
}
