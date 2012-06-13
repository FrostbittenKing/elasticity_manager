package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Assignment;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.exception.StepNotReproducableException;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class MoveVMStep implements IOperationStep {
	private PhysicalMachine source;
	private PhysicalMachine destination;
	private VirtualMachine movedVirtualMachine;

	public MoveVMStep(PhysicalMachine source, PhysicalMachine destination,
			VirtualMachine movedVirtualMachine) {
		super();
		this.source = source;
		this.destination = destination;
		this.movedVirtualMachine = movedVirtualMachine;
	}

	public PhysicalMachine getSource() {
		return source;
	}

	public PhysicalMachine getDestination() {
		return destination;
	}

	public VirtualMachine getMovedVirtualMachine() {
		return movedVirtualMachine;
	}

	@Override
	public void execute(Assignment currentState) throws StepNotReproducableException {
		/*
		 * get actual source pm from currentstate by comparing ids
		 * get actual destination pm the same way
		 * get actual virtual machine by comparing ids from source pm member vms
		 * then move the vm
		 */
		PhysicalMachine realSource = null;
		PhysicalMachine realDestination = null;
		
		for (PhysicalMachine pm : currentState.getRunningPhysicalMachines()) {
			if (pm.getId() == source.getId()) {
				realSource = pm;
			}
			if (pm.getId() == destination.getId()) {
				realDestination = pm;
			}
		}
		
		if (realDestination == null || realSource == null) {
			throw new StepNotReproducableException();
		}
		
		// TODO use these
		source.removeVM(movedVirtualMachine);
		destination.addVM(movedVirtualMachine);
//		movedVirtualMachine.stopJobs();
//		source.getVirtualMachines().remove(movedVirtualMachine.getId());
//		destination.getVirtualMachines().put(movedVirtualMachine.getId(), movedVirtualMachine);
//		movedVirtualMachine.resumeJobs();
	}

	@Override
	public double getCosts() {
		Double totalPricePerCycle = (double) source.getPricePerCycle() + destination.getPricePerCycle();
		
		Double pricePMPerAttr = (double) totalPricePerCycle / (double) 3;
	
		Double totalCPUaffected = (double) source.getCPUs() + (double) destination.getCPUs();
		Double totalDiskaffected = (double) source.getDiskMemory() + (double) destination.getDiskMemory();
		Double totalMemoryaffected = (double) source.getMemory() + (double) destination.getMemory();
		
		Double shareCPU = (double) movedVirtualMachine.getTotalAvailableCPUs() / totalCPUaffected;
		Double shareDisk = (double) movedVirtualMachine.getTotalAvailableDiskMemory() / totalDiskaffected;
		Double shareRam = (double) movedVirtualMachine.getTotalAvailableMemory() / totalMemoryaffected;
		
		return (JobScheduler.getInstance().getVirtualMachineMigrationCost() * ((shareCPU + shareDisk + shareRam) * pricePMPerAttr));
	}
}
