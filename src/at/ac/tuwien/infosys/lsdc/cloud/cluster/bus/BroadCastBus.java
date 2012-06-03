package at.ac.tuwien.infosys.lsdc.cloud.cluster.bus;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import at.ac.tuwien.infosys.lsdc.cloud.cluster.bus.messages.Request;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.bus.messages.Request.RequestType;
import at.ac.tuwien.infosys.lsdc.cloud.cluster.bus.messages.ResourceRequest;

public class BroadCastBus {
	private static BroadCastBus _instance ;
	private static final int BUS_SERVER_PORT = 51234;
	private static final int BUS_CLIENT_PORT = 51235;
	private static InetAddress targetAddress;
	private static DatagramSocket serverSocket;
	private BroadCastBus() throws ClusterBusInitializationException {
		try {
			serverSocket = new DatagramSocket(BUS_SERVER_PORT);
			targetAddress = InetAddress.getByName("localhost");
			serverSocket.setBroadcast(true);
		} catch (SocketException e) {
			System.err.println("cannot initialize Clusterbus");
			throw new ClusterBusInitializationException(e.getMessage());
		} catch (UnknownHostException e) {
			throw new ClusterBusInitializationException(e.getMessage());
		}
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
	}
	
	public static BroadCastBus getInstance() throws ClusterBusInitializationException {
		if (_instance == null) {
			_instance = new BroadCastBus();
		}
		return _instance;
	}
	
	private void sendMessage(String message) throws ClusterBusSendException {
		byte [] sendData = message.getBytes();
		DatagramPacket packet = new DatagramPacket(sendData, sendData.length, targetAddress, BUS_CLIENT_PORT );
		try {
			serverSocket.send(packet);
		} catch (IOException e) {
			throw new ClusterBusSendException(e.getMessage());
		}
	}
	
	public void sendResourceRequest(Integer diskMemory, Integer memory, Integer cpus) throws ClusterBusSendException {
		ResourceRequest resourceRequest = new ResourceRequest(diskMemory, memory, cpus);
		Request request = new Request(RequestType.RESOURCE_REQUEST, resourceRequest);
		sendMessage(makeJsonMessage(request));
	}
	
	private String makeJsonMessage(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object, object.getClass());
		
	}
	
}
