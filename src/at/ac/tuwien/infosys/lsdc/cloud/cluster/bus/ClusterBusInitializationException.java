package at.ac.tuwien.infosys.lsdc.cloud.cluster.bus;

public class ClusterBusInitializationException extends Exception {

	/**
	 * 
	 */
	private String message;
	private static final long serialVersionUID = -711595105628342114L;

	public ClusterBusInitializationException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	
}
