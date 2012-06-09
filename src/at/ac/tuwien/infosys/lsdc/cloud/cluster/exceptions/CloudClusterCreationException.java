package at.ac.tuwien.infosys.lsdc.cloud.cluster.exceptions;

public class CloudClusterCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3899206395376168339L;
	private String message;
	private Throwable cause;
	
	public CloudClusterCreationException(String message, Throwable cause) {
		super(message,cause);
		this.message = message;
		this.cause = cause;	
	}
	
	public CloudClusterCreationException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	
	
	
}
