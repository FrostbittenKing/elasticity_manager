package at.ac.tuwien.infosys.lsdc.simulation.monitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.TimerTask;

public class Monitor extends TimerTask{
	private File outputFile;
	private FileOutputStream outputStream;
	
	public Monitor(String outputFileName) throws IOException{
		outputFile = new File(outputFileName);
		outputFile.createNewFile();
		outputStream = new FileOutputStream(outputFile);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
