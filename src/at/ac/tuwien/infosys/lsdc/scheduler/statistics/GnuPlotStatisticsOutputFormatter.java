package at.ac.tuwien.infosys.lsdc.scheduler.statistics;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GnuPlotStatisticsOutputFormatter implements
IStatisticsOutputFormatter {

	private static final char HEADER_COLUMN_NAME_START = (char)((int)'a' - 1);
	private static final char HEADER_COLUMN_START  = '#';
	private String fileName = null;
	
	public GnuPlotStatisticsOutputFormatter(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public void writeDataToFile(Object[][] data, OutputMode mode) throws StatisticsWriterException {
		int nrOfColumns = data[0].length;
		int x = HEADER_COLUMN_NAME_START;
		File outputFile = new File(fileName);
		PrintWriter fileOutput = null;
		try {
			String headerLine = "";
			switch (mode) {
			case OVERWRITE:
				if (outputFile.exists()) {
					outputFile.delete();
				}

				outputFile.createNewFile();
				fileOutput = new PrintWriter(fileName);
				headerLine = String.valueOf(HEADER_COLUMN_START);
				for ( int i = 0; i < nrOfColumns; i++) {
					headerLine += "\t" + String.valueOf((char)++x) + "\n";
				}
				fileOutput.write(headerLine);
				break;
			case APPEND:
				headerLine = String.valueOf(HEADER_COLUMN_START);
				if (!outputFile.exists()) {
					outputFile.createNewFile();
					for ( int i = 0; i < nrOfColumns; i++) {
						headerLine += "\t" + String.valueOf((char)++x) + "\n";
					}
					fileOutput = new PrintWriter(new FileWriter(outputFile,true),true);
					fileOutput.append(headerLine);
				}
				else {
					fileOutput = new PrintWriter(new FileWriter(outputFile,true),true);
				}
				
				
				break;
			}
			
		}
		catch (IOException e) {
			throw new StatisticsWriterException(e);
		}

		for (Object[] currentLine : data) {
			String currentOutputLine = "";
			for (Object value : currentLine) {
				currentOutputLine += "\t" + (String)value; 
			}
			fileOutput.append(currentOutputLine + "\n");
		}
		fileOutput.close();
	}

}

