package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation;

import at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.exception.OperationNotSupportedException;

public class OperationFactory {
	private static OperationFactory instance = null;
	
	private MoveJobOperation moveJobOperation = null;
	private MoveVirtualMachineOperation moveVirtualMachineOperation = null;
	private SwapJobOperation swapJobOperation = null;
	private SwapVirtualMachineOperation swapVirtualMachineOperation = null;
	private StartPhysicalMachineOperation startPhysicalMachineOperation = null;
	private StartVirtualMachineOperation startVirtualMachineOperation = null;
	private RedistributePhysicalMachineOperation redistributePhysicalMachineOperation = null;
	private RedistributeVirtualMachineOperation redistributeVirtualMachineOperation = null;
	
	private OperationFactory(){
		moveJobOperation = new MoveJobOperation();
		moveVirtualMachineOperation = new MoveVirtualMachineOperation();
		swapJobOperation = new SwapJobOperation();
		swapVirtualMachineOperation = new SwapVirtualMachineOperation();
		startPhysicalMachineOperation = new StartPhysicalMachineOperation();
		startVirtualMachineOperation = new StartVirtualMachineOperation();
		redistributePhysicalMachineOperation = new RedistributePhysicalMachineOperation();
		redistributeVirtualMachineOperation = new RedistributeVirtualMachineOperation();
	}
	
	public IOperation getOperation(OperationType type) throws OperationNotSupportedException{
		switch(type){
			case Move_Job:
				return moveJobOperation;
			case Move_VM:
				return moveVirtualMachineOperation;
			case Swap_Job:
				return swapJobOperation;
			case Swap_VM:
				return swapVirtualMachineOperation;
			case Start_PM:
				return startPhysicalMachineOperation;
			case Start_VM:
				return startVirtualMachineOperation;
			case Redistribute_PM:
				return redistributePhysicalMachineOperation;
			case Redistribute_VM:
				return redistributeVirtualMachineOperation;
			default:
				throw new OperationNotSupportedException(type.toString());
		}
	}
	
	public static OperationFactory getInstance(){
		if (instance == null){
			instance = new OperationFactory();
		}
		return instance;
	}
}
