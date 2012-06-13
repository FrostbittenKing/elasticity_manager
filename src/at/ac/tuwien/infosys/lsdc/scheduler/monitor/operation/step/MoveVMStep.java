package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class MoveVMStep extends OperationStep {
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
	public void execute() {
		movedVirtualMachine.stopJobs();
		source.getVirtualMachines().remove(movedVirtualMachine.getId());
		destination.getVirtualMachines().put(movedVirtualMachine.getId(), movedVirtualMachine);
		movedVirtualMachine.resumeJobs();
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
