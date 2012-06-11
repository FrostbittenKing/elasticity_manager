package at.ac.tuwien.infosys.lsdc.scheduler;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.OutsourcedJob;

public interface IOutsourcedJobCompletionListener {
	public void jobCompleted(OutsourcedJob job);
}
