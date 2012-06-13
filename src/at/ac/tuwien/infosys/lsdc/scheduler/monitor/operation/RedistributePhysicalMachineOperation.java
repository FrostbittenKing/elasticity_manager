package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.heuristics.BestFit;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Assignment;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Change;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.MoveVMStep;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.exception.StepNotReproducableException;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class RedistributePhysicalMachineOperation implements IOperation {

	@Override
	public ArrayList<Change> execute(Assignment source) {
		ArrayList<Change> solutions = new ArrayList<Change>();
		
		pmLoop: for (int i = 0; i < source.getRunningPhysicalMachines().length; i++){
			PhysicalMachine[] PMs = source.getRunningPhysicalMachines().clone();
			Change change = new Change(source, new Assignment(PMs, source.getStoppedPhysicalMachines().clone()));
			
			for(VirtualMachine currentVM : PMs[i].getVirtualMachines().values()){
				BestFit<PhysicalMachine> bestFit = new BestFit<PhysicalMachine>(PMs);
				
				PhysicalMachine target = (PhysicalMachine) bestFit.getBestFittingMachine(currentVM);
				if (target == null)
					continue pmLoop;
				
				MoveVMStep step = new MoveVMStep(PMs[i], target, currentVM);
				try {
					step.execute(source);
				} catch (StepNotReproducableException e) {
					e.printStackTrace();
				}
				change.addStep(step);
				
			}
			solutions.add(change);
		}
		SolutionReducer.reduce(solutions);
		
		return solutions;
	}

}
