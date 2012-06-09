package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import java.util.Vector;

import at.ac.tuwien.infosys.lsdc.scheduler.IJobEventListener;

public class VirtualMachine{
	private Integer totalAvailableMemory = null;
	private Integer totalAvailableCPUs = null;
	private Integer totalAvailableDiskMemory = null;
	
	private Integer currentUsedTotalMemory = 0;
	private Integer currentUsedTotalCPUs = 0;
	private Integer currentUsedTotalDiskMemory = 0;
	
	private Vector<Job> runningJobs = new Vector<Job>();
	
	private transient Integer id = 0;

	public VirtualMachine(Integer id, Integer diskSize, Integer memorySize, Integer numCPUs){
		this.totalAvailableCPUs = numCPUs;
		this.totalAvailableDiskMemory = diskSize;
		this.totalAvailableMemory = memorySize;
		this.id = id;
	}
	
	public synchronized void addJob(Job job){
		runningJobs.add(job);
		currentUsedTotalCPUs += job.getConsumedCPUs();
		currentUsedTotalDiskMemory += job.getConsumedDiskMemory();
		currentUsedTotalMemory += job.getConsumedMemory();
		new Thread(job).start();
	}
	
	public void jobCompleted(Job job) {
		runningJobs.remove(job);
		currentUsedTotalCPUs -= job.getConsumedCPUs();
		currentUsedTotalDiskMemory -= job.getConsumedDiskMemory();
		currentUsedTotalMemory -= job.getConsumedMemory();	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
