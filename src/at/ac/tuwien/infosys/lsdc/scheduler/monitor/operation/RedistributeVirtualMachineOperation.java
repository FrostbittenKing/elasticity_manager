package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Assignment;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Change;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.MoveJobStep;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class RedistributeVirtualMachineOperation implements IOperation {

	@Override
	public ArrayList<Change> execute(Assignment source) {
		ArrayList<Change> solutions = new ArrayList<Change>();

		PhysicalMachine[] runningPhysicalMachines = source.getRunningPhysicalMachines();
		for (int i = 0; i < runningPhysicalMachines.length; i++){
			PhysicalMachine[] machines = runningPhysicalMachines.clone();
			Map<Integer, VirtualMachine> virtualMachines = machines[i].getVirtualMachines();

			synchronized(virtualMachines) {
				vmLoop: for(VirtualMachine currentVM : virtualMachines.values()){
					Change newChange = new Change(source, new Assignment(machines, source.getStoppedPhysicalMachines().clone()));
					InsourcedJob[] currentVMJobs = currentVM.getRunningJobs().keySet().toArray(new InsourcedJob[0]);
					for(int j = 0; j < currentVM.getRunningJobs().size(); j++){

						//						BestFit<VirtualMachine> bestFit = new BestFit<VirtualMachine>(
						//								machines[i].getVirtualHostingCandidates(currentVMJobs[j]).toArray(new VirtualMachine[0]));
						//						VirtualMachine destination = (VirtualMachine)bestFit.getBestFittingMachineIgnoreCurrent(currentVMJobs[j]);

						List<VirtualMachine> candidates = new ArrayList<VirtualMachine>();
						for (int k = 0; k < machines.length; k++) {
							candidates.addAll(machines[k].getVirtualMachines().values());
						}
						candidates.remove(currentVM);
						List<VirtualMachine> candidates2 = new ArrayList<VirtualMachine>();
						for (VirtualMachine vm : candidates) {
							if (vm.canHostJob(currentVMJobs[j]))
								candidates2.add(vm);
						}

						VirtualMachine destination = null;

						if (candidates2.size() > 0) {
							if (candidates2.size() == 1) {
								destination = candidates2.get(0);
							} else {
								destination = candidates2.get(Math.abs(new Random().nextInt() % (candidates2.size() - 1)));
							}
						}

						if (destination == null){
							continue vmLoop;
						}
						MoveJobStep step = new MoveJobStep(currentVM, destination, currentVMJobs[j]);
						step.execute();
						newChange.addStep(step);					
					}
					solutions.add(newChange);
				}
			}
		}
		SolutionReducer.reduce(solutions);
		return solutions;
	}
}
