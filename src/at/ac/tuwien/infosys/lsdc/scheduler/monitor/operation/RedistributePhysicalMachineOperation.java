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

			VirtualMachine[] currentPMVMs = PMs[i].getVirtualMachines().values().toArray(new VirtualMachine[0]);
			for(int j = 0; j < currentPMVMs.length; j++){
				BestFit<PhysicalMachine> bestFit = new BestFit<PhysicalMachine>(getFittingPhysicalMachines(PMs, currentPMVMs[j]));

				PhysicalMachine target = (PhysicalMachine) bestFit.getBestFittingMachine(currentPMVMs[j]);
				if (target == null)
					continue pmLoop;

				MoveVMStep step = new MoveVMStep(PMs[i], target, currentPMVMs[j]);
				step.execute(source);
				change.addStep(step);

			}
			solutions.add(change);
		}
		SolutionReducer.reduce(solutions);

		return solutions;
	}
	
	private PhysicalMachine[] getFittingPhysicalMachines(PhysicalMachine[] machines, VirtualMachine vm){
		ArrayList<PhysicalMachine> machineCandidates = new ArrayList<PhysicalMachine>();
		for (int i = 0; i < machines.length; i++){
			if (machines[i].canHostVirtualMachine(vm)){
				machineCandidates.add(machines[i]);
			}
		}
		return machineCandidates.toArray(new PhysicalMachine[0]);
	}

}
