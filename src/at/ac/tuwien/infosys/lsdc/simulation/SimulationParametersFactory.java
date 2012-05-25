package at.ac.tuwien.infosys.lsdc.simulation;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class SimulationParametersFactory {
	private static SimulationParametersFactory instance = null;
	
	private FileReader simulationPropertiesReader = null;
	private Gson gson = new Gson();
	
	private SimulationParametersFactory(){
		
	}
	
	public SimulationParameters createParameters(String filePath) throws FileNotFoundException, JsonSyntaxException, JsonIOException{
		simulationPropertiesReader = new FileReader(filePath);
		return gson.fromJson(simulationPropertiesReader, SimulationParameters.class);
	}
	
	public static SimulationParametersFactory getInstance(){
		if (instance == null){
			instance = new SimulationParametersFactory();
		}
		return instance;
	}

}
