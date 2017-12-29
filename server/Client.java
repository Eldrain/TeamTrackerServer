package server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import messanger.handler.Handler;
import messanger.handler.Messanger;
import messanger.message.Message;

/**
 * Class for communication with client and store information about him
 * @author ANovikov
 *
 */
public class Client implements Runnable {
	/**
	 * @param mId - ID of client
	 * @param mSocket - socket of client
	 * @param mServerMessanger - messanger for sending messages to server
	 * @param mHandler - handler of messages to client
	 */
	
	private int mId;
	private Socket mSocket;
	
	private Messanger mServerMessanger;
	private Handler mHandler;
	
	private String mProtectStr;
	
	private DataOutputStream out;
	private DataInputStream in; 
	
	//private Gson mGson;
	
	public Client(Socket socket, int id, Messanger sMes, String protectStr) {
		mId = id;
		mSocket = socket;
		mServerMessanger = sMes;
		//mGson = new Gson();
		
		mHandler = new Handler() {

			@Override
			public void handle(Message message) {
				try {
					switch(message.code) {
					case Message.OUT_INFO:
						out.writeUTF(message.info);
						out.flush();
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}	
		};
		mProtectStr = protectStr;
	}
	
	public Messanger getMessanger() {
		return (Messanger) mHandler.getMessanger();
	}

	@Override
	public void run() {
		try {
			out = new DataOutputStream(mSocket.getOutputStream());
			in = new DataInputStream(mSocket.getInputStream());
			
			out.writeInt(mId);
			out.flush();
			
			String login = null;
			login = in.readUTF();
			
			mServerMessanger.send(new Message(Message.IN_NEW_CLIENT, mId, login));
			
			out.writeUTF(mProtectStr);
			out.flush();
		
			while(!mSocket.isClosed()) {
				String data = null;
				
				data = in.readUTF();
				
				mServerMessanger.send(new Message(Message.IN_NEW_TASK, mId, data));
				mHandler.waitMessage();
				mHandler.handle();
			}
		
		} catch (IOException e) {
			System.out.println("Problem with client" + mId + "!");
			try {
				mSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	public void closeConnection() {
		try {
			mSocket.close();
		} catch (IOException e) {
			System.out.println("Error when closing socket!! Client ID = " + mId);
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return mId;
	}
}
