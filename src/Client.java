/**
 * Defines common methods for the two client types
 */
public interface Client {
    public void sendMessage(Message msg);
    public void disconnect();
    public boolean start();
}
