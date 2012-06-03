package at.ac.tuwien.infosys.lsdc.cloud.cluster;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public interface ICloudCluster {
	void addPhysicalMachine(PhysicalMachine machine);
	Boolean jobFits(Job job);
	void addJob(Integer machineId, Job job);
}
