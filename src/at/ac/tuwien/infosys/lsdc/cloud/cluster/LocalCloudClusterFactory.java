package at.ac.tuwien.infosys.lsdc.cloud.cluster;

import java.util.HashMap;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.exceptions.CloudClusterCreationException;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.simulation.config.SimulationParameters;
import at.ac.tuwien.infosys.lsdc.simulation.config.SimulationParametersFactory;

public class LocalCloudClusterFactory implements ICloudClusterFactory {

	private static final String SIMULATION_PROPERTIES_FILENAME = "simulation_properties.json";
	
	private static LocalCloudClusterFactory _instance = new LocalCloudClusterFactory();
	
	private LocalCloudClusterFactory() {
		
	}
	
	public static ICloudClusterFactory getInstance() {
		return _instance;
	}
	@Override
	public ICloudClusterManager createLocalCluster(HashMap<Integer, PhysicalMachine> machines) {
		return new CloudCluster(machines);
	}

}
