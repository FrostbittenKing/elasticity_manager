package at.ac.tuwien.infosys.lsdc.scheduler.objects;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import com.google.gson.Gson;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.bus.BusListenerClientException;

public class PhysicalMachine {
	private Integer id = null;
	private Integer CPUs = null;
	private Integer memory = null;
	private Integer diskMemory = null;
	private Integer pricePerCycle = null;
	private transient Boolean isRunning = false;
	private transient MulticastSocket busSocket = null;
	private transient int BusPort = 51235;
	
	public void enableBusListener() throws BusListenerClientException {
		try {
			busSocket = new MulticastSocket(BusPort);
			InetAddress group = InetAddress.getByName("224.0.0.0");
			busSocket.joinGroup(group);
		} catch (IOException e) {
			throw new BusListenerClientException(e.getMessage());
		}
	}
	
	public Integer getPricePerCycle() {
		return pricePerCycle;
	}
	public Boolean isRunning() {
		return isRunning;
	}
	public void setPricePerCycle(Integer pricePerCycle) {
		this.pricePerCycle = pricePerCycle;
	}
	public void setIsRunning(Boolean isRunning) {
		this.isRunning = isRunning;
	}	
	
	public Integer getCPUs() {
		return CPUs;
	}
	public Integer getMemory() {
		return memory;
	}
	public Integer getDiskMemory() {
		return diskMemory;
	}
	public void setCPUs(Integer cPUs) {
		CPUs = cPUs;
	}
	public void setMemory(Integer memory) {
		this.memory = memory;
	}
	public void setDiskMemory(Integer diskMemory) {
		this.diskMemory = diskMemory;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "PhysicalMachine [CPUs=" + CPUs + ", memory=" + memory
				+ ", diskMemory=" + diskMemory + ", pricePerCycle="
				+ pricePerCycle + ", isRunning=" + isRunning + "]";
	}
	
	private class BusListener implements Runnable {

		private MulticastSocket socket;
		
		public BusListener(MulticastSocket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			byte[] buf = new byte[1024];
		    DatagramPacket packet = new DatagramPacket(buf, buf.length);
		    String received = new String(packet.getData());
		    Gson gson = new Gson();
		    
		    try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
	}
}
