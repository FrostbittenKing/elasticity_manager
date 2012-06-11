package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.IResourceInformation;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.Resource;

public class VirtualMachine extends Machine implements IResourceInformation{
	private Integer totalAvailableMemory = null;
	private Integer totalAvailableCPUs = null;
	private Integer totalAvailableDiskMemory = null;
	
	private Integer currentUsedTotalMemory = 0;
	private Integer currentUsedTotalCPUs = 0;
	private Integer currentUsedTotalDiskMemory = 0;
	
	private transient PhysicalMachine host = null;
	
	private HashMap<InsourcedJob, Thread> runningJobThreads = new HashMap<InsourcedJob, Thread>();
	
	private transient Integer id = 0;

	public VirtualMachine(PhysicalMachine host, Integer id, Integer diskSize, Integer memorySize, Integer numCPUs){
		this.totalAvailableCPUs = numCPUs;
		this.totalAvailableDiskMemory = diskSize;
		this.totalAvailableMemory = memorySize;
		this.id = id;
		this.host = host;
	}
	
	public synchronized void addJob(InsourcedJob job){
		Thread jobThread = new Thread(job);
		runningJobThreads.put(job, jobThread);
		currentUsedTotalCPUs += job.getConsumedCPUs();
		currentUsedTotalDiskMemory += job.getConsumedDiskMemory();
		currentUsedTotalMemory += job.getConsumedMemory();
		jobThread.start();
	}
	
	public void jobCompleted(InsourcedJob job) {
		runningJobThreads.remove(job);
		currentUsedTotalCPUs -= job.getConsumedCPUs();
		currentUsedTotalDiskMemory -= job.getConsumedDiskMemory();
		currentUsedTotalMemory -= job.getConsumedMemory();	
	}
	
	public void stopJobs(){
		for (Thread currentJobThread : runningJobThreads.values()){
			currentJobThread.interrupt();
		}
	}
	
	public void resumeJobs(){
		for (InsourcedJob currentJob : runningJobThreads.keySet()){
			Thread newJobThread = new Thread(currentJob);
			runningJobThreads.put(currentJob, newJobThread);
			newJobThread.start();
		}
	}
	
	

	public Integer getTotalAvailableMemory() {
		return totalAvailableMemory;
	}

	public Integer getTotalAvailableCPUs() {
		return totalAvailableCPUs;
	}

	public Integer getTotalAvailableDiskMemory() {
		return totalAvailableDiskMemory;
	}

	public Integer getCurrentUsedTotalMemory() {
		return currentUsedTotalMemory;
	}

	public Integer getCurrentUsedTotalCPUs() {
		return currentUsedTotalCPUs;
	}

	public Integer getCurrentUsedTotalDiskMemory() {
		return currentUsedTotalDiskMemory;
	}
	
	public boolean canHostJob(InsourcedJob job) {
		return job.getConsumedCPUs() <= (totalAvailableCPUs - currentUsedTotalCPUs) && 
				job.getConsumedMemory() <= (totalAvailableMemory - currentUsedTotalMemory) &&
				job.getConsumedDiskMemory() <= (totalAvailableDiskMemory - currentUsedTotalDiskMemory);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Resource getUsedResources() {
		return new Resource(id, currentUsedTotalCPUs, currentUsedTotalMemory, currentUsedTotalDiskMemory);
	}

	@Override
	public Resource getTotalResources() {
		return new Resource(id, totalAvailableCPUs, totalAvailableMemory, totalAvailableDiskMemory);
	}

	public PhysicalMachine getHost() {
		return host;
	}
	
	
}
