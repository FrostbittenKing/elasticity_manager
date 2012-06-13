package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step;

import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Assignment;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.exception.StepNotReproducableException;

public interface IOperationStep {
	public abstract void execute(Assignment currentStates) throws StepNotReproducableException;
	public abstract double getCosts();
	public abstract Object getSource();
	public abstract Object getDestination();

}
