package at.ac.tuwien.infosys.lsdc.scheduler;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;

public interface IJobCompletionCallBack {
	public void completeJob(Job job);
}
