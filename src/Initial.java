import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Initial extends JFrame{
	private String host, username;
	private int port;
	private String protocol;
	private boolean isConnected = true;
	
	public static void main(String[] args) {
		Initial initial = new Initial();
	}
	
	Initial(){
		prompt();
	}
	public void prompt(){
		

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
		if(this.protocol.equals("TCP")){
			Client client = new Client(this.host, this.port, this.username, this.protocol);
			if(client.start()){
				isConnected = true;
			}
		}else{
			UDPClient client = new UDPClient(this.host,  this.port, this.username, this.protocol);
			client.start();
		}
	}
}
	

	