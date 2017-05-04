/**
 * Created by henrykirk on 5/3/17.
 */
public interface Client {
    public void sendMessage(Message msg);
    public void disconnect();
    public boolean start();
}
