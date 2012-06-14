package at.ac.tuwien.infosys.lsdc.simulation.monitor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.GnuPlotOutputDataConverter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.GnuPlotStatisticsOutputFormatter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.IStatisticsOutputWriter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.IStatisticsOutputWriter.OutputMode;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.PhysicalMachineUsage;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.StatisticsWriterException;

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
		
		/*ArrayList<PhysicalMachineUsage> currentUsage = JobScheduler.getInstance().getCurrentUsage();
		PhysicalMachine[] runningMachines = JobScheduler.getInstance().getCluster().getRunningMachines();
		*/
		Double costs = getRelativeCosts();
		Double relativeUsage = getRelativePMUsage();
		
		IStatisticsOutputWriter outputFormatter = new GnuPlotStatisticsOutputFormatter(fileName);
		try {
			String[][] writerInput = GnuPlotOutputDataConverter.doubleInput(
					new Double[][]{
							new Double[]{new Double(timeIndex),costs,relativeUsage}
							});
			outputFormatter.writeDataToFile(
					writerInput, OutputMode.APPEND);
			timeIndex += tickRate;
		} catch (StatisticsWriterException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private Double getRelativeCosts() {
		Double costs = 0.0;
		PhysicalMachine[] runningMachines = JobScheduler.getInstance().getCluster().getRunningMachines();
		PhysicalMachine[] stoppedMachines = JobScheduler.getInstance().getCluster().getOfflineMachines();
		for (PhysicalMachine currentMachine : runningMachines) {
			costs += currentMachine.getPricePerCycle();
			
		}
		Double maxCosts = costs.doubleValue();
		for (PhysicalMachine currentOfflineMachine : stoppedMachines) {
			maxCosts += currentOfflineMachine.getPricePerCycle();
		}
		return costs / maxCosts;
	}
	
	private Double getRelativePMUsage() {
		ArrayList<PhysicalMachineUsage> currentUsage = JobScheduler.getInstance().getCurrentUsage();
		Double relativeUsage = 0.0;
		for (PhysicalMachineUsage currentMachine : currentUsage) {
			relativeUsage += currentMachine.getUsageLoad();
		}
		return (currentUsage.size() > 0 ? relativeUsage / currentUsage.size() : 0.0);
	}
	
	

}
