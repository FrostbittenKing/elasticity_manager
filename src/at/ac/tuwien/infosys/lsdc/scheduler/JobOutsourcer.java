package at.ac.tuwien.infosys.lsdc.scheduler;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;

public class JobOutsourcer {
	private static JobOutsourcer instance = null;
	private Double outSourceCostsPerCycle = null;
	
	private JobOutsourcer(){
		
	}
	
	public void outSourceJob(Job job){
		
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
}
