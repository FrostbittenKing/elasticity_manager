package at.ac.tuwien.infosys.lsdc.cloud.cluster.bus.messages;

public class Request {
	
	public Request(RequestType type, Object message) {
		this.type = type;
		this.message = message;
	}
	
	public RequestType type;
	public Object message;
	
	public enum RequestType {
		RESOURCE_REQUEST
	}

}
