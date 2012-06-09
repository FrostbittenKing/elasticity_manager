package at.ac.tuwien.infosys.lsdc.simulation;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.ICloudClusterFactory;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.ICloudClusterManager;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.LocalCloudClusterFactory;
import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.simulation.config.SimulationParameters;
import at.ac.tuwien.infosys.lsdc.simulation.config.SimulationParametersFactory;
import at.ac.tuwien.infosys.lsdc.tools.RandomGaussNumber;

public class Simulation {
	private static final String SIMULATION_PROPERTIES_FILENAME = "simulation_properties.json";
	public static void main(String [] args) throws InterruptedException{

		SimulationParameters parameters;
		try {
			parameters = SimulationParametersFactory.getInstance().createParameters(SIMULATION_PROPERTIES_FILENAME);
			
			ICloudClusterFactory cloudClusterFactory = LocalCloudClusterFactory.getInstance();
			ICloudClusterManager cluster = cloudClusterFactory.createLocalCluster(parameters.getPhysicalMachines());

			JobScheduler.getInstance().initialize(cluster);
	

			for (int i = 0; i < parameters.getNumberOfJobs(); i++){
				JobScheduler.getInstance().scheduleJob(createJob(parameters));
				Thread.sleep((long)parameters.getJobSchedulingDelay());
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	return	RandomGaussNumber.newGaussianInt(lowerBound, upperBound);
	}
}
