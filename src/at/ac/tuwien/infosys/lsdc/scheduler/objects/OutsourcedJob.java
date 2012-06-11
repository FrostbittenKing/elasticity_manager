package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import at.ac.tuwien.infosys.lsdc.scheduler.IOutsourcedJobCompletionListener;

public class OutsourcedJob extends Job{
	private Double outsourceCloudCostsPerCycle = null;
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
		calculateExecutionCosts(remainingExecutionTime);
	}



	@Override
	protected void calculateExecutionCosts(Long executedTime) {
		executionCosts += (executedTime / 1000) * outsourceCloudCostsPerCycle;
	}

	public void setOutsourceCloudCostsPerCycle(Double outsourceCloudCostsPerCycle) {
		this.outsourceCloudCostsPerCycle = outsourceCloudCostsPerCycle;
	}

	public void setListener(IOutsourcedJobCompletionListener listener) {
		this.listener = listener;
	}


}
