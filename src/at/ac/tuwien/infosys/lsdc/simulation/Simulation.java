package at.ac.tuwien.infosys.lsdc.simulation;

import java.io.FileNotFoundException;

import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class Simulation {
	private static final String SIMULATION_PROPERTIES_FILENAME = "simulation_properties.json";
	public static void main(String [] args) throws InterruptedException{
		SimulationParameters parameters = null;
		try {
			parameters = SimulationParametersFactory.getInstance().createParameters(SIMULATION_PROPERTIES_FILENAME);
		}
		catch (JsonSyntaxException e) {
			e.printStackTrace();
			return;
		}
		catch (JsonIOException e) {
			e.printStackTrace();
			return;
		}
		catch (FileNotFoundException e) {
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
		//TODO: someone else can dick around with this shit, use java.util.Random.nextGaussian()
		return 5000;
	}
}
