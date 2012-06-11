package at.ac.tuwien.infosys.lsdc.scheduler.exception;

public class IllegalValueException extends Exception {
	private static final long serialVersionUID = 1222265107050925742L;

	String error = "unknown";

	public IllegalValueException(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

}
