package at.ac.tuwien.infosys.lsdc.scheduler.monitor.operation.exception;

public class OperationNotSupportedException extends Exception {

	private static final long serialVersionUID = -4270007777548593920L;

	public OperationNotSupportedException() {
	}

	public OperationNotSupportedException(String message) {
		super(message);
	}

	public OperationNotSupportedException(Throwable cause) {
		super(cause);
	}

	public OperationNotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}
}
