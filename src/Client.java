import java.net.*;
import java.io.*;

/*
 * Client is run from ClientGUI
 */
public class Client  {
	// to read from socket
	private ObjectInputStream sInput;
	// to write to socket
	private ObjectOutputStream sOutput;
	private Socket socket;
	private ClientGUI gui;
	// the host server, the port, and the username
	private String host, username;
	private int port;

	/*
	 * Constructor
	 */
	Client(String host, int port, String username, ClientGUI gui) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.gui = gui;
	}

	/*
	 * Try to connect to Server
	 */
	public boolean start() {
		// try to connect to server
		try {
			socket = new Socket(host, port);
		} catch(Exception e) {
			gui.displayMessage("Error connecting to server: " + e);
			return false;
		}

		String msg = "You connected to " + socket.getInetAddress() + " on port " + socket.getPort();
		gui.displayMessage(msg);

		try {
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			gui.displayMessage("Exception creating I/O streams: " + eIO);
			return false;
		}
		// creates the Thread to listen from the server
		new ListenFromServer().start();
		// Send Client's username to the server
//		try {
//			sOutput.writeObject(username);
			sendMessage(new Message(Message.USERNAME, username));
//		}
//		catch (IOException eIO) {
//			gui.displayMessage("Exception IO: " + eIO);
//			disconnect();
//			return false;
//		}
		// success
		return true;
	}

	/*
	 * Send a message to Server
	 */
	public void sendMessage(Message msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			gui.displayMessage("Exception writing to server: " + e);
		}
	}

	/*
	 * Disconnect Client from Server
	 */
	public void disconnect() {
		try {
			if(sInput != null) sInput.close();
		} catch(Exception e) {}
		try {
			if(sOutput != null) sOutput.close();
		} catch(Exception e) {}
		try{
			if(socket != null) socket.close();
		} catch(Exception e) {}
		// inform the GUI that connection failed
		gui.failedToConnect();
	}

	/*
	 * Listen for messages broadcasted by Server
	 */
	class ListenFromServer extends Thread {
		public void run() {
			while(true) {
				try {
					String msg = (String) sInput.readObject();
					gui.displayMessage(msg);
				} catch(IOException e) {
					gui.displayMessage("Exception connecting: " + e);
					gui.failedToConnect();
					break;
				} catch(ClassNotFoundException e) {}
			}
		}
	}
}