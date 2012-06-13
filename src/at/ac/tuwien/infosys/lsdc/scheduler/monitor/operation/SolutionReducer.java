package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Change;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class SolutionReducer {

	public static void reduce(ArrayList<Change> solutions) {
		for (Change currentChange : solutions) {
			List<PhysicalMachine> PMs = Arrays.asList(currentChange.getDestination().getRunningPhysicalMachines());
			for (PhysicalMachine PM : PMs) {
				for (VirtualMachine VM : PM.getVirtualMachines().values()) {
					if (VM.getRunningJobs().isEmpty()) {
						// TODO: shutoff VM
						PM.getVirtualMachines().remove(VM.getId());
					}
				}
				if (PM.getVirtualMachines().isEmpty()) {
					// TODO: shutoff PM
					PMs.remove(PM);
				}
			}
			currentChange.getDestination().setRunningPhysicalMachines(PMs.toArray(new PhysicalMachine[0]));
		}
	}
}
