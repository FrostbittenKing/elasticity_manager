package at.ac.tuwien.infosys.lsdc.scheduler.monitor;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.OperationStep;

public class Change {
	private Assignment source;
	private Assignment destination;
	private ArrayList<OperationStep> steps = new ArrayList<OperationStep>(); 
	
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
	
	public void addStep(OperationStep step){
		steps.add(step);
	}
}
