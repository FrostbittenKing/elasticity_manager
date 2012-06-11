package at.ac.tuwien.infosys.lsdc.scheduler;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;

public interface IJobEventListener {
	public void jobCompleted(InsourcedJob job);
	public void jobAdded(InsourcedJob job);
}
