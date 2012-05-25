package at.ac.tuwien.infosys.lsdc.scheduler;

import java.util.ArrayList;

import at.ac.tuwien.infosys.lsdc.scheduler.objects.Job;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.PhysicalMachine;
import at.ac.tuwien.infosys.lsdc.scheduler.objects.VirtualMachine;

public class JobScheduler {
	private static JobScheduler instance = null;
	
	private ArrayList<PhysicalMachine> physicalMachines = new ArrayList<PhysicalMachine>();
	private ArrayList<VirtualMachine> virtualMachines = new ArrayList<VirtualMachine>();
	
	private JobScheduler(){
		
	}
	
	public void initialize(ArrayList<PhysicalMachine> physicalMachines){
		this.physicalMachines.addAll(physicalMachines);
	}
	
	public void scheduleJob(Job job){
		System.out.println("Scheduled job: " + job + " , WOOHOO!");
		//TODO: magic happens here
	}
	
	public static JobScheduler getInstance(){
		if (instance == null){
			instance = new JobScheduler();
		}
		return instance;
	}

}
