package at.ac.tuwien.infosys.lsdc.simulation.monitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;

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
		ArrayList<PhysicalMachineUsage> currentUsage = JobScheduler.getInstance().getCurrentUsage();
		
	}

}
