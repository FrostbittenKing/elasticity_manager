package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Assignment;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Change;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class RedistributePhysicalMachineOperation implements IOperation {

	@Override
	public ArrayList<Change> execute(Assignment source) {
		ArrayList<Change> solutions = new ArrayList<Change>();
		
		PhysicalMachine[] PMs = source.getRunningPhysicalMachines().clone();
		
		for (PhysicalMachine currentPM : PMs){
			for(VirtualMachine currentVM : currentPM.getVirtualMachines().values()){
				// move this VM to other PM
				
			}
			// add created solution
			Assignment destination = new Assignment(
					PMs, 
					source.getStoppedPhysicalMachines());
//			reduce(destination);
			
			solutions.add(new Change(source, destination));
		}
		return solutions;
	}

}
