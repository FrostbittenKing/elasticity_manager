package at.ac.tuwien.infosys.lsdc.cloud.cluster.bus;

public class ClusterBusSendException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5923391606949474293L;
	private String message;
	
	public ClusterBusSendException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
	
	
}
