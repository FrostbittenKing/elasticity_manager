package at.ac.tuwien.infosys.lsdc.scheduler;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;

public interface IJobEventListener {
	public void jobCompleted(Job job);
	public void jobAdded(Job job);
}
