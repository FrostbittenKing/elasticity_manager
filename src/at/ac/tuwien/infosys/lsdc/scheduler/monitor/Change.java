package at.ac.tuwien.infosys.lsdc.scheduler.monitor;

public class Change {
	private Assignment source;
	private Assignment destination;
	
	public Change(Assignment source, Assignment destination){
		this.source = source;
		this.destination = destination;
	}

	public Assignment getSource() {
		return source;
	}

	public Assignment getDestination() {
		return destination;
	}
}
