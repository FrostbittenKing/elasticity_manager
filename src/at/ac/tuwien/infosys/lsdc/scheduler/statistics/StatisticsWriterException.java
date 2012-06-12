package at.ac.tuwien.infosys.lsdc.scheduler.statistics;

import java.io.IOException;

public class StatisticsWriterException extends IOException {

	private static final long serialVersionUID = 6383483329278241018L;

	public StatisticsWriterException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public StatisticsWriterException(String message) {
		super(message);
	
	}

	public StatisticsWriterException(Throwable cause) {
		super(cause);
	
	}

	
}
