package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.Assignment;
import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step.exception.StepNotReproducableException;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class MoveJobStep implements IOperationStep {
	private VirtualMachine source;
	private VirtualMachine destination;
	private InsourcedJob movedJob;
	
	public MoveJobStep(VirtualMachine source, VirtualMachine destination,
			InsourcedJob movedJob) {
		super();
		this.source = source;
		this.destination = destination;
		this.movedJob = movedJob;
	}

	public VirtualMachine getSource() {
		return source;
	}

	public VirtualMachine getDestination() {
		return destination;
	}

	public InsourcedJob getMovedJob() {
		return movedJob;
	}

	@Override
	public void execute() {
		source.removeJob(movedJob);
		destination.addJob(movedJob);
	}
	
	@Override
	public void execute(Assignment currentState) throws StepNotReproducableException {
		
		VirtualMachine realSource = null;
		VirtualMachine realDestination = null;
		
		for (PhysicalMachine pm : currentState.getRunningPhysicalMachines()) {
			for (VirtualMachine vm : pm.getVirtualMachines().values()) {
				if (realSource.getId() == vm.getId())
					realSource = vm;
				if (realDestination.getId() == vm.getId())
					realDestination = vm;
			}
		}
		
		if (realSource == null || realDestination == null) {
			throw new StepNotReproducableException();
		}
		
		realSource.removeJob(movedJob);
		realDestination.addJob(movedJob);
	}

	@Override
	public double getCosts() {		
		Double totalPricePerCycle = (double) source.getHost().getPricePerCycle() + destination.getHost().getPricePerCycle();
		
		Double pricePMPerAttr = (double) totalPricePerCycle / (double) 3;
	
		Double totalCPUaffected = (double) source.getHost().getCPUs() + (double) destination.getHost().getCPUs();
		Double totalDiskaffected = (double) source.getHost().getDiskMemory() + (double) destination.getHost().getDiskMemory();
		Double totalMemoryaffected = (double) source.getHost().getMemory() + (double) destination.getHost().getMemory();
		
		Double shareCPU = (double) movedJob.getConsumedCPUs() / totalCPUaffected;
		Double shareDisk = (double) movedJob.getConsumedDiskMemory() / totalDiskaffected;
		Double shareRam = (double) movedJob.getConsumedMemory() / totalMemoryaffected;
		
		return (JobScheduler.getInstance().getVirtualMachineMigrationCost() * ((shareCPU + shareDisk + shareRam) * pricePMPerAttr));
	}
}
