import java.io.*;
import java.net.*;
import java.util.*;


public class UDPServer {
	private ServerGUI gui;
	private int port;
	private boolean isRunning;


	public UDPServer(int port, ServerGUI gui){
		this.port = port;
		this.gui = gui;
	
	}
	
	
	public void start() {
		isRunning = true;
		System.out.println(port);
		
		try{
			DatagramSocket ds = new DatagramSocket(port);
			while(isRunning){
				DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
				try{
					ds.receive(packet);
					String message = new String (packet.getData());
					message = message.trim();
					gui.displayEvent("Message from: " + packet.getAddress() + "- " + message);
				}catch(IOException e){
						System.out.println("Error2");
				}
			}
		}catch(SocketException e){
			System.out.println("Error");
		}
	}

	public void stop() {
		isRunning = false;
		try {
			new Socket("localhost", port);
		} catch(Exception e) {}
	}

}