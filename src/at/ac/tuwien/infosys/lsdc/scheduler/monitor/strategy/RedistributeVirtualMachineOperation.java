package at.ac.tuwien.infosys.lsdc.scheduler.monitor.strategy;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.heuristics.BestFit;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Assignment;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Change;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class RedistributeVirtualMachineOperation implements IOperation {

	@Override
	public ArrayList<Change> execute(Assignment source) {
		ArrayList<Change> solutions = new ArrayList<Change>();
		for (PhysicalMachine currentPM : source.getRunningPhysicalMachines()){
			for(VirtualMachine currentVM : currentPM.getVirtualMachines().values()){
				BestFit<PhysicalMachine> bestFit = new BestFit<PhysicalMachine>(source.getRunningPhysicalMachines());
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

}
