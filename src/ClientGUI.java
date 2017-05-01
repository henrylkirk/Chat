import javax.swing.*;
import java.awt.*;

/*
 * ClientGUI
 */
public class ClientGUI extends JFrame {

	// to hold the Username and later on the messages
	private JTextField tfMessage;
	// to display messages
	private JTextArea chatRoom;
	private boolean isConnected = true;
	private Client client;
	private String username, protocol, host;
	private int port;

	// Constructor connection receiving a socket number
	ClientGUI() {
		// Prompt for user input
		showDialog();

        // NORTH
        // Create JButton to disconnect from server and quit program
        JPanel jpNorth = new JPanel();
        jpNorth.setPreferredSize(new Dimension(500, 50));
        JButton jbDisconnect = new JButton("Disconnect");
        jbDisconnect.addActionListener(e -> {
            // send disconnect Message when clicked
            client.sendMessage(new Message(Message.DISCONNECT, null));
            client.disconnect();
            System.exit(0);
        });
        // Add JButton to JPanel
        jpNorth.add(jbDisconnect);
        // Add JPanel to JFrame
        add(jpNorth,BorderLayout.NORTH);

        // SOUTH
        // JPanel for border south
        JPanel jpSouth = new JPanel();

        // Create JTextField for user inputs and add it to south panel
        tfMessage = new JTextField(20);
        jpSouth.add(tfMessage);

        // Create JButton for sending messages and add it to south panel
        jpSouth.setPreferredSize(new Dimension(500, 50));
        JButton jbSend = new JButton("Send");
        // Add action listener to get client message
        jbSend.addActionListener(e -> {
            if(isConnected) {
                // send message
                client.sendMessage(new Message(Message.MESSAGE, tfMessage.getText()));
                tfMessage.setText("");
            }
        });
        jpSouth.add(jbSend);

        // Add send text field and button panel to JFrame
        add(jpSouth,BorderLayout.SOUTH);

        // Create JPanel
        JPanel jpClient = new JPanel();
        chatRoom = new JTextArea(20, 45);
        chatRoom.setEditable(false); // make it read-only
        chatRoom.setLineWrap(true);
        JScrollPane scroll = new JScrollPane(chatRoom);
        jpClient.add(scroll);

        // Add text area panel to JFrame
        add(jpClient);

        // Set JFrame property
        setTitle("Client GUI");
        setLocation(380, 150);
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Create a new client with this GUI
        client = new Client(this.host, this.port, this.username, this.protocol, this);
        if(client.start()){
            isConnected = true;
        }
	}

	// Add message to GUI's chatRoom
	public void displayMessage(String str) {
		chatRoom.append(str + "\n");
	}

	// called by the Client if the connection failed
	public void failedToConnect() {
		isConnected = false;
	}

	// Show dialog - get client input
	private void showDialog(){
        JTextField inputName = new JTextField();
        JTextField inputProtocol = new JTextField("TCP");
        JTextField inputHost = new JTextField("localhost");
        JTextField inputPort = new JTextField("1678");
        Object[] inputMessages = {
                "Enter Your name:", inputName,
                "TCP or UDP:", inputProtocol,
                "Enter host:", inputHost,
                "Enter Port:", inputPort
        };
        int option = JOptionPane.showConfirmDialog(null, inputMessages, "Enter all your values", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION){
            // check valid username input
            if(inputName.getText().trim().length() == 0){
                this.username = "Anonymous";
            } else {
                this.username = inputName.getText().trim();
            }
            // check valid protocol input
            if(inputProtocol.getText().trim().length() == 0 || inputProtocol.getText().charAt(0) == 'T'){
                this.protocol = "TCP";
            } else {
                this.protocol = "UDP";
            }
            // check valid host input
            if(inputHost.getText().trim().length() == 0){
                this.host = "localhost";
            } else {
                this.host = inputHost.getText().trim();
            }
            // check not empty port input
            if(inputPort.getText().trim().length() != 0){
                // try to parse port input to int
                try {
                    this.port = Integer.parseInt(inputPort.getText().trim());
                }
                catch(Exception e) {
                    System.out.println(e);
                }
            } else {
                this.port = 1678; // set to default port
            }
        }
	}

	public static void main(String[] args) {
		ClientGUI gui = new ClientGUI();
	}

}