package at.ac.tuwien.infosys.lsdc.scheduler.monitor;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

public class Assignment implements Comparable<Assignment>{
	private PhysicalMachine[] runningPhysicalMachines = null;
	private PhysicalMachine[] stoppedPhysicalMachines = null;
	private ArrayList<Change> neighborAssignments = new ArrayList<Change>();
	
	private Integer runningCosts = null;
	private Integer migrationCosts = null;
	
	public Assignment(PhysicalMachine[] runningMachines, PhysicalMachine[]stoppedPhysicalMachines){
		runningCosts = 0;
		migrationCosts = 0;
		
		this.runningPhysicalMachines = runningMachines.clone();
		this.stoppedPhysicalMachines = stoppedPhysicalMachines.clone();
		for(PhysicalMachine currentMachine : runningMachines){
			runningCosts += currentMachine.getPricePerCycle();
		}
	}

	public Integer getRunningCosts() {
		return runningCosts;
	}

	public Integer getMigrationCosts() {
		return migrationCosts;
	}

	public PhysicalMachine[] getRunningPhysicalMachines() {
		return runningPhysicalMachines;
	}
	
	public void setRunningPhysicalMachines(PhysicalMachine[] pms) {
		this.runningPhysicalMachines = pms;
	}

	public PhysicalMachine[] getStoppedPhysicalMachines() {
		return stoppedPhysicalMachines;
	}
	
	public void addNeighbor(Change change){
		neighborAssignments.add(change);
	}
	
	@Override
	public int compareTo(Assignment o) {
//		return new Integer(runningCosts + migrationCosts).compareTo(o.getRunningCosts() + o.getMigrationCosts());
		return (runningCosts - o.runningCosts);
	}
}