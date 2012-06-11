package at.ac.tuwien.infosys.lsdc.simulation.monitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;

public class Monitor extends TimerTask{
	private BufferedWriter writer = null;
	
	public Monitor(String outputFileName) throws IOException{
		this.writer = new BufferedWriter(new FileWriter(outputFileName));
	}
	
	@Override
	public void run() {
		ArrayList<PhysicalMachineUsage> currentUsage = JobScheduler.getInstance().getCurrentUsage();
		
		
	}

}
