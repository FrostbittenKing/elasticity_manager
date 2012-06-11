import java.io.FileNotFoundException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.CloudCluster;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.LocalCloudClusterFactory;
import at.ac.tuwien.infosys.lsdc.scheduler.JobScheduler;
import at.ac.tuwien.infosys.lsdc.scheduler.heuristics.BestFit;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.InsourcedJob;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.simulation.config.SimulationParameters;
import at.ac.tuwien.infosys.lsdc.simulation.config.SimulationParametersFactory;
import at.ac.tuwien.infosys.lsdc.tools.RandomGaussNumber;


public class Test {

	private static final String SIMULATION_PROPERTIES_FILENAME = "simulation_properties.json";
	public static void main(String[] args) throws JsonSyntaxException, JsonIOException, FileNotFoundException  {
		// TODO Auto-generated method stub
	
		SimulationParameters parameters = SimulationParametersFactory.getInstance().createParameters(SIMULATION_PROPERTIES_FILENAME);
		
		LocalCloudClusterFactory cloudClusterFactory = LocalCloudClusterFactory.getInstance();
		CloudCluster cluster = cloudClusterFactory.createLocalCluster(parameters.getPhysicalMachines());

	//	JobScheduler.getInstance().initialize(parameters.getPhysicalMachines());
//		InsourcedJob job = createJob(parameters);
		/*
		PhysicalMachine machine = cluster.getPhysicalMachine(1);
		
		Resource currentUsedResources = machine.getUsedResources();
		Resource totalResources = machine.getTotalResources();
		currentUsedResources.addJob(job);
		
		
		Matrix<Double> x = new Matrix<Double>(Double.class ,1,3);
		Matrix<Double> y = new Matrix<Double>(Double.class ,1,3);
		x.setMatrix(new Object[]{currentUsedResources.getResources()});
		y.setMatrix(new Object[]{totalResources.getResources()});
		x.divElement(y);
		
		PMLoadMatrix load = new PMLoadMatrix(1, 3);
		PMLoadMatrix all = new PMLoadMatrix(1, 3);
		load.addResource(currentUsedResources);
		all.addResource(totalResources);
		load.divElement(all);
		Matrix<Double> foo = MatrixHelper.calculateRowMean(load);
		*/
//		cluster.startMachine();
//		cluster.startMachine();
//		cluster.startMachine();
		BestFit<PhysicalMachine> bestFit = new BestFit<PhysicalMachine>(cluster.getRunningMachines());
//		System.out.println(((PhysicalMachine)bestFit.getBestFittingMachine(job)).getId());
		//Matrix<Double>z = MatrixHelper.calculateRowMean(x);
	}
	
	private static Integer generateRandomInteger(Integer lowerBound, Integer upperBound){
		return lowerBound + (int)(Math.random() * ((upperBound - lowerBound) + 1));
	}
	
//	private static InsourcedJob createJob(SimulationParameters parameters){
//		return new InsourcedJob(
//				generateRandomInteger(parameters.getMinDiskSize(), parameters.getMaxDiskSize()),
//				generateRandomInteger(parameters.getMinMemorySize(), parameters.getMaxMemorySize()),
//				generateRandomInteger(parameters.getMinCPUCount(), parameters.getMaxCPUCount()),
//				generateNormalDistributedInteger(parameters.getMinExecutionTime(), parameters.getMaxExecutionTime())
//		);
//	}
//
//	private static Integer generateNormalDistributedInteger(Integer lowerBound, Integer upperBound){
//	return	RandomGaussNumber.newGaussianLong(lowerBound, upperBound);
//	}

}
