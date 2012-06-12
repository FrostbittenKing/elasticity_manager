package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step;

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
		// TODO Auto-generated method stub
		
	}
}
