package at.ac.tuwien.infosys.lsdc.simulation.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class SimulationParametersDeserializer implements JsonDeserializer<SimulationParameters>{

	private static final String PM_PROPERTY  = "physicalMachines";
	private static final String CONV_METHOD_NAME_PREFIX = "getAs";

	/**
	 * Deserializes a value from the json element and sets the value in the targetInstance
	 * @param targetInstance instance of the class in which the setter is invoked
	 * @param key the json key
	 * @param value the value set in targetInstance
	 */
	public void setDeserializedElement(Object targetInstance, String key, JsonElement value) {
		try {
			Class<?> targetClass = targetInstance.getClass();
			Field field = targetClass.getDeclaredField(key);
			Class<?> fieldType = field.getType();


			Method convertMethod;
			String convertMethodName;
			if (fieldType.getSimpleName().matches("Integer")) {
				convertMethodName = CONV_METHOD_NAME_PREFIX + "Int";
			//	System.out.println("fetching method " + convertMethodName + " for key: " + key + " on Object " + value);
				convertMethod  = JsonElement.class.getMethod(CONV_METHOD_NAME_PREFIX + "Int");
			} 
			else {
				convertMethodName = CONV_METHOD_NAME_PREFIX + fieldType.getSimpleName();
				System.out.println("fetching method " + convertMethodName + " for key: " + key + " on Object " + value);
				convertMethod  = JsonElement.class.getMethod(convertMethodName);
			}
//			System.out.println("invoking method " + convertMethodName + " for key: " + key + " on: " + value);
			Object deserializedValue = convertMethod.invoke(value);
			String elementSetterName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);

			Method elementSetter = targetClass.getMethod(elementSetterName, fieldType);
			elementSetter.invoke(targetInstance, deserializedValue);

//			System.out.println("next");
		}
		catch (NoSuchFieldException e) {
			System.err.println("field: " + key + "not found, skipping");
		}
		catch (NoSuchMethodException e) {
			System.err.println(e.getMessage());
		}
		catch (InvocationTargetException e) {

		}
		catch (IllegalAccessException e) {

		}

	}
	/**
	 * deserializes Physical Machines
	 * it invokes deserializeMachine for each physicalMachine in the Array
	 * @param arg0 array of json encoded physical machines
	 * @return a hashmap of physical machines
	 */
	private HashMap<Integer, PhysicalMachine> deserializePhysicalMachines(JsonArray arg0) {
		HashMap<Integer, PhysicalMachine> theMachines = new HashMap<Integer, PhysicalMachine>();
		for (JsonElement currentMachine : arg0) {
			PhysicalMachine deserializedMachine = deserializePhysicalMachine(currentMachine);
			deserializedMachine.setId(IdGenerator.generateNewId());
			theMachines.put(deserializedMachine.getId(), deserializedMachine);
		}
		return theMachines;
	}
	
	/**
	 * deserializes a found physical machine
	 * @param machine the machine to be deserialized
	 * @return deserialized machine
	 */
	private PhysicalMachine deserializePhysicalMachine(JsonElement machine) {
		Gson gson = new Gson();
		return gson.fromJson(machine, PhysicalMachine.class);
	}

	@Override
	public SimulationParameters deserialize(JsonElement arg0, Type arg1,
			JsonDeserializationContext arg2) throws JsonParseException {
		SimulationParameters simulationParameters = new SimulationParameters();

		
		JsonObject obj = arg0.getAsJsonObject();
		Iterator<Entry<String, JsonElement>> entrySetIterator = obj.entrySet().iterator();
		while(entrySetIterator.hasNext()) {
			Entry<String, JsonElement> entry = entrySetIterator.next();
			if (entry == null)
				return null;


			if (entry.getValue().isJsonPrimitive()) {
				setDeserializedElement(simulationParameters, entry.getKey(), entry.getValue());
			}

			if (entry.getKey().matches(PM_PROPERTY)) {
				simulationParameters.setPhysicalMachines(deserializePhysicalMachines(entry.getValue().getAsJsonArray()));
			}
		}	
		return simulationParameters;
	}

	private static class IdGenerator {
		private static Integer currentId = 1;
		
		public static Integer generateNewId() {
			return currentId++;
		}
	}


}
