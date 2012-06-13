package at.ac.tuwien.infosys.lsdc.simulation.monitor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.GnuPlotOutputDataConverter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.GnuPlotStatisticsOutputFormatter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.IStatisticsOutputFormatter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.IStatisticsOutputFormatter.OutputMode;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.StatisticsWriterException;
import at.ac.tuwien.infosys.lsdc.simulation.Simulation;

public class Monitor extends TimerTask{
	//private BufferedWriter writer = null;
	private String fileName = null;
	private long timeIndex = 0;
	private long tickRate;
	
	public Monitor(String outputFileName, long tickRate) throws IOException{
		this.fileName = outputFileName;
		this.tickRate = tickRate;
	}
	
	@Override
	public void run() {
		
		ArrayList<PhysicalMachineUsage> currentUsage = JobScheduler.getInstance().getCurrentUsage();
		PhysicalMachine[] runningMachines = JobScheduler.getInstance().getCluster().getRunningMachines();
		
		Double costs = 0.0;
		for (PhysicalMachine currentMachine : runningMachines) {
			costs += currentMachine.getPricePerCycle();
		}
		
		IStatisticsOutputFormatter outputFormatter = new GnuPlotStatisticsOutputFormatter(fileName);
		try {
			outputFormatter.writeDataToFile(GnuPlotOutputDataConverter.doubleInput(new Double[][]{new Double[]{new Double(timeIndex),costs}}), OutputMode.APPEND);
			timeIndex += tickRate;
		} catch (StatisticsWriterException e) {
			System.err.println(e.getMessage());
		}
		//TODO
		// plotting usage/machine -- HISTOGRAM y: %, x: machines
		// total costs of all machines over timey: sum costs, x: time
	}

}
