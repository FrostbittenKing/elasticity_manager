package at.ac.tuwien.infosys.lsdc.scheduler.statistics;

public interface IStatisticsOutputFormatter {
	public void writeDataToFile(Object[][] data, OutputMode mode) throws StatisticsWriterException;
	
	public enum OutputMode{
		OVERWRITE,
		APPEND
	}
}
