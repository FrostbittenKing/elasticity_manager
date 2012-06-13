package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Change;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class SolutionReducer {

	public static void reduce(ArrayList<Change> solutions) {
		for (Change currentChange : solutions) {
			List<PhysicalMachine> PMs = Arrays.asList(currentChange.getDestination().getRunningPhysicalMachines());
			reduce(PMs);
			currentChange.getDestination().setRunningPhysicalMachines(PMs.toArray(new PhysicalMachine[0]));
		}
	}

	private static void reduce(List<PhysicalMachine> PMs) {
		for (PhysicalMachine PM : PMs) {
			for (VirtualMachine VM : PM.getVirtualMachines().values()) {
				if (VM.getRunningJobs().isEmpty()) {
					// TODO repair
					PM.removeVM(VM);
					PM.getVirtualMachines().remove(VM.getId());
				}
			}
			if (PM.getVirtualMachines().isEmpty()) {
				// TODO repair
				PM.shutdown();
			}
		}
	}
	
	public static void reduce(CloudCluster cluster) {
		cluster.cleanup();
		//reduce(Arrays.asList(cluster.getRunningMachines()));
	}
}
