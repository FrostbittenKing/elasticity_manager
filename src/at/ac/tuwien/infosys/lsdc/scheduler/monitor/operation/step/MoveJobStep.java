package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.step;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class MoveJobStep extends OperationStep {
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
		source.getRunningJobs().remove(movedJob);
		destination.getRunningJobs().put(movedJob, new Thread(movedJob));
	}
}
