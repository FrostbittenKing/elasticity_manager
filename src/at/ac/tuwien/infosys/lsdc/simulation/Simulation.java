package at.ac.tuwien.infosys.lsdc.simulation;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;

public class Simulation {
	private static final String SIMULATION_PROPERTIES_FILENAME = "simulation_properties.json";
	public static void main(String [] args) throws InterruptedException{
		SimulationParameters parameters = null;
		try {
			parameters = SimulationParametersFactory.getInstance().createParameters(SIMULATION_PROPERTIES_FILENAME);
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}

		JobScheduler.getInstance().initialize(parameters.getPhysicalMachines());

		for (int i = 0; i < parameters.getNumberOfJobs(); i++){
			JobScheduler.getInstance().scheduleJob(createJob(parameters));
			Thread.sleep((long)parameters.getJobSchedulingDelay());
		}
	}

	private static Job createJob(SimulationParameters parameters){
		return new Job(
				generateRandomInteger(parameters.getMinDiskSize(), parameters.getMaxDiskSize()),
				generateRandomInteger(parameters.getMinMemorySize(), parameters.getMaxMemorySize()),
				generateRandomInteger(parameters.getMinCPUCount(), parameters.getMaxCPUCount()),
				generateNormalDistributedInteger(parameters.getMinExecutionTime(), parameters.getMaxExecutionTime())
		);
	}

	private static Integer generateRandomInteger(Integer lowerBound, Integer upperBound){
		return lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1));
	}

	private static Integer generateNormalDistributedInteger(Integer lowerBound, Integer upperBound){
		//TODO: someone else can dick around with this shit, use java.util.Random.nextGaussian() or whatever
		return 5000;
	}
}
