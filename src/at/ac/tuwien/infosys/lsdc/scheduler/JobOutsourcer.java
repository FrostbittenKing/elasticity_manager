package at.ac.tuwien.infosys.lsdc.scheduler;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.OutsourcedJob;

public class JobOutsourcer implements IOutsourcedJobCompletionListener{
	private static JobOutsourcer instance = null;
	private Double outSourceCostsPerCycle = null;
	
	private JobOutsourcer(){
		
	}
	
	public void outSourceJob(OutsourcedJob job){
		job.setListener(this);
		new Thread(job).start();
	}
	
	public void setOutsourceCosts(Double costs){
		this.outSourceCostsPerCycle = costs;
	}
	
	public static JobOutsourcer getInstance(){
		if (instance == null){
			instance = new JobOutsourcer();
		}
		return instance;
	}

	@Override
	public void jobCompleted(OutsourcedJob job) {
		// TODO Auto-generated method stub
		
	}
}
