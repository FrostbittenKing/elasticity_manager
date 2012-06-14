package at.ac.tuwien.infosys.lsdc.scheduler.monitor;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.IOperationStep;

public class Change implements Comparable<Change>{
	private Assignment source;
	private Assignment destination;
	private ArrayList<IOperationStep> steps = new ArrayList<IOperationStep>(); 
	
	public Change(Assignment source, Assignment destination){
		this.source = source;
		this.destination = destination;
	}

	public Assignment getSource() {
		return source;
	}

	public Assignment getDestination() {
		return destination;
	}
	
	public void addStep(IOperationStep step){
		steps.add(step);
	}

	public ArrayList<IOperationStep> getSteps() {
		return steps;
	}

	@Override
	public int compareTo(Change other) {
		return destination.compareTo(other.destination);
	}

	public void setSource(Assignment source) {
		this.source = source;
	}
	
	
}
