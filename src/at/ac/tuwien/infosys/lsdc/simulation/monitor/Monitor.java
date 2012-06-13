package at.ac.tuwien.infosys.lsdc.simulation.monitor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.GnuPlotOutputDataConverter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.GnuPlotStatisticsOutputFormatter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.IStatisticsOutputFormatter;
import at.ac.tuwien.infosys.lsdc.scheduler.statistics.IStatisticsOutputFormatter.OutputMode;
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
		
		IStatisticsOutputFormatter outputFormatter = new GnuPlotStatisticsOutputFormatter(fileName);
		try {
			outputFormatter.writeDataToFile(
					GnuPlotOutputDataConverter.doubleInput(new Double[][]{new Double[]{new Double(timeIndex),costs,relativeUsage}}), OutputMode.APPEND);
			timeIndex += tickRate;
		} catch (StatisticsWriterException e) {
			System.err.println(e.getMessage());
		}
		//TODO
		// plotting usage/machine -- HISTOGRAM y: %, x: machines
		// total costs of all machines over timey: sum costs, x: time
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
		return relativeUsage / currentUsage.size();
	}
	
	

}
