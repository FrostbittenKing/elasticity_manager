package at.ac.tuwien.infosys.lsdc.cloud.cluster.exceptions;

public class PhysicalMachineException extends Exception{
	private static final long serialVersionUID = -1977980198609444274L;

	public PhysicalMachineException() {
		super();
	}


	public PhysicalMachineException(String message, Throwable cause) {
		super(message, cause);
	}

	public PhysicalMachineException(String message) {
		super(message);
	}

	public PhysicalMachineException(Throwable cause) {
		super(cause);
	}
}
