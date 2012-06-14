package at.ac.tuwien.infosys.lsdc.scheduler;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.OutsourcedJob;

public class JobOutsourcer implements IOutsourcedJobCompletionListener{
	private static JobOutsourcer instance = null;
	private Double outSourceCostsPerCycle = null;
	private Double currentCosts = null;
	private Double totalSumCosts = null;
	private Integer currentNumberOutsourcedJobs = null;
	private Integer totalNumberOutsourcedjObs = null;
	
	private JobOutsourcer(){		
		totalSumCosts = 0.0;
		currentCosts = 0.0;
		currentNumberOutsourcedJobs = 0;
		totalNumberOutsourcedjObs = 0;
	}
	
	public synchronized void outSourceJob(OutsourcedJob job){
		System.out.println("Outsourcing job.");
		job.setListener(this);
		Double jobCosts = job.getExecutionTime() * outSourceCostsPerCycle;
		currentCosts += jobCosts;
		totalSumCosts += jobCosts;
		currentNumberOutsourcedJobs ++;
		totalNumberOutsourcedjObs ++;
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
	public synchronized void jobCompleted(OutsourcedJob job) {
		System.out.println("Outsourced job finished: " + job.toString());
		currentCosts -= job.getExecutionTime() * outSourceCostsPerCycle;	
		currentNumberOutsourcedJobs --;
	}

	public Double getCurrentCosts() {
		return currentCosts;
	}

	public Double getTotalSumCosts() {
		return totalSumCosts;
	}

	public Integer getCurrentNumberOutsourcedJobs() {
		return currentNumberOutsourcedJobs;
	}

	public Integer getTotalNumberOutsourcedjObs() {
		return totalNumberOutsourcedjObs;
	}
}
