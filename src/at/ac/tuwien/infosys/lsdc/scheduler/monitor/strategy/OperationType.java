package at.ac.tuwien.infosys.lsdc.scheduler.monitor.strategy;

public enum OperationType {
	Start_VM,
	Start_PM,
	Move_VM,
	Swap_VM,
	Move_Job,
	Swap_Job,
	Redistribute_PM,
	Redistribute_VM;
}
