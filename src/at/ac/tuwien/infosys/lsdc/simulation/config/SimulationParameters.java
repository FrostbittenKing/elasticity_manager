package at.ac.tuwien.infosys.lsdc.simulation.config;

import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public class SimulationParameters {
	private Integer minCPUCount = null;
	private Integer maxCPUCount = null;
	
	private Integer minMemorySize = null;
	private Integer maxMemorySize = null;
	
	private Integer minDiskSize = null;
	private Integer maxDiskSize = null;
	
	private Integer minExecutionTime = null;
	private Integer maxExecutionTime = null;
	
	private Integer numberOfJobs = null;
	private Integer jobSchedulingDelay = null;
	
	private Double jobMigrationCost = null;
	private Double virtualMachineMigrationCost = null;
	private Double physicalMachineBootCost = null;
	private Double outSourceCostsPerCycle = null;
	
	public Integer getNumberOfJobs() {
		return numberOfJobs;
	}

	public Integer getJobSchedulingDelay() {
		return jobSchedulingDelay;
	}

	public void setNumberOfJobs(Integer numberOfJobs) {
		this.numberOfJobs = numberOfJobs;
	}

	public void setJobSchedulingDelay(Integer jobSchedulingDelay) {
		this.jobSchedulingDelay = jobSchedulingDelay;
	}

	private HashMap<Integer,PhysicalMachine> physicalMachines = null;
	
	public Integer getMinExecutionTime() {
		return minExecutionTime;
	}

	public Integer getMaxExecutionTime() {
		return maxExecutionTime;
	}

	public void setMinExecutionTime(Integer minExecutionTime) {
		this.minExecutionTime = minExecutionTime;
	}

	public void setMaxExecutionTime(Integer maxExecutionTime) {
		this.maxExecutionTime = maxExecutionTime;
	}

	public Integer getMinCPUCount() {
		return minCPUCount;
	}

	public Integer getMaxCPUCount() {
		return maxCPUCount;
	}

	public Integer getMinMemorySize() {
		return minMemorySize;
	}

	public Integer getMaxMemorySize() {
		return maxMemorySize;
	}

	public Integer getMinDiskSize() {
		return minDiskSize;
	}

	public Integer getMaxDiskSize() {
		return maxDiskSize;
	}

	public HashMap<Integer,PhysicalMachine> getPhysicalMachines() {
		return physicalMachines;
	}

	public void setMinCPUCount(Integer minCPUCount) {
		this.minCPUCount = minCPUCount;
	}

	public void setMaxCPUCount(Integer maxCPUCount) {
		this.maxCPUCount = maxCPUCount;
	}

	public void setMinMemorySize(Integer minMemorySize) {
		this.minMemorySize = minMemorySize;
	}

	public void setMaxMemorySize(Integer maxMemorySize) {
		this.maxMemorySize = maxMemorySize;
	}

	public void setMinDiskSize(Integer minDiskSize) {
		this.minDiskSize = minDiskSize;
	}

	public void setMaxDiskSize(Integer maxDiskSize) {
		this.maxDiskSize = maxDiskSize;
	}

	public void setPhysicalMachines(HashMap<Integer,PhysicalMachine> physicalMachines) {
		this.physicalMachines = physicalMachines;
	}

	public Double getJobMigrationCost() {
		return jobMigrationCost;
	}

	public Double getVirtualMachineMigrationCost() {
		return virtualMachineMigrationCost;
	}

	public Double getPhysicalMachineBootCost() {
		return physicalMachineBootCost;
	}

	public void setJobMigrationCost(Double jobMigrationCost) {
		this.jobMigrationCost = jobMigrationCost;
	}

	public void setVirtualMachineMigrationCost(Double virtualMachineMigrationCost) {
		this.virtualMachineMigrationCost = virtualMachineMigrationCost;
	}

	public void setPhysicalMachineBootCost(Double physicalMachineBootCost) {
		this.physicalMachineBootCost = physicalMachineBootCost;
	}

	public Double getOutSourceCostsPerCycle() {
		return outSourceCostsPerCycle;
	}

	public void setOutSourceCostsPerCycle(Double outSourceCostsPerCycle) {
		this.outSourceCostsPerCycle = outSourceCostsPerCycle;
	}
}
