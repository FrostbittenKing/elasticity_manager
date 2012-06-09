package at.ac.tuwien.infosys.lsdc.cloud.cluster.bus;

public class BusListenerClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1459260383637531007L;
	String message;
	
	public BusListenerClientException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	
	
}
