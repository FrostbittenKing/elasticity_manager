package at.ac.tuwien.infosys.lsdc.cloud.cluster;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public interface ICloudClusterManager {
	void addPhysicalMachine(PhysicalMachine machine);
	Boolean jobFits(Job job);
	void addJob(Integer machineId, Job job);
	Boolean startMachine();
	PhysicalMachine getPhysicalMachine(Integer id);
	PhysicalMachine[] getRunningMachines();
}
