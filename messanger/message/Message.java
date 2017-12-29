package messanger.message;

/**
 * 28.12.2017.
 * Class for storing information
 */
public class Message implements IMessage {
    public int code;
    public int client;
    public String info;
    
    public Message(int code, int client, String info) {
    	this.code = code;
    	this.client = client;
    	this.info = info;
    }

    public static final int OUT_INFO = 0;
    public static final int IN_NEW_CLIENT = 1;
    public static final int IN_NEW_TASK = 2;
}
