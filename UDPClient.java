import java.net.*;
import java.io.*;


public class UDPClient {
	
	private DatagramSocket dSocket;
	private UDPClientGUI gui;
	// the host server, the port, and the username
	private String host, username;
	private int port;
	private String protocol;

	/*
	 * Constructor
	 */
	UDPClient(String host, int port, String username, String protocol) {
		this.host = host;
		this.port = port;
		this.username = username;
        this.protocol = protocol;
		
	}

	
	public void start() {
		
		this.gui = new UDPClientGUI(this);
		
		try { 
			dSocket = new DatagramSocket();
			System.out.println("Client Socket made");
		}catch (Exception e) {
			gui.displayMessage("Error");
		}
		
		String msg = "Please enter your message....";
		gui.displayMessage(msg);
		
		
	}

	public void sendMessage(Message msg) {
		if(msg.getType() == 2){
			this.disconnect();
			return;
		}
		try {
			String message = msg.getContent();
			byte[] data = message.getBytes();
			InetAddress addr = InetAddress.getByName(host);
			DatagramPacket pack = new DatagramPacket(data, data.length, addr, port);
			System.out.println(addr + " " + port);
			try{
				dSocket.send(pack);
			}catch(PortUnreachableException e){
				gui.displayMessage("Exception2");
			}
			gui.displayMessage(message);
		}
		catch(IOException e) {
			gui.displayMessage("Exception writing to server: " + e);
		}
	}
	
	public void disconnect() {
		dSocket.close();
	}


}