package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import data.TData;

/**
 * Class for communication with client and store information about him
 * @author ANovikov
 *
 */
public class Client implements Runnable {
	/**
	 * mId - ID of client
	 * mSocket - socket of client
	 * mServerInput - array for sending information (TData) to server
	 * mOutput - to Client
	 */
	
	private int mId;
	private Socket mSocket;
	private ArrayList<TData> mServerInput;
	private LinkedList<String> mOutput;
	
	public Client(Socket socket, int id, ArrayList<TData> inputBuffer) {
		mId = id;
		mSocket = socket;
		mServerInput = inputBuffer;
		mOutput = new LinkedList<String>();
	}
	
	public void send(String data) {
		System.out.println("Send to client with id = " + mId);
		mOutput.addFirst(data);
	}

	@Override
	public void run() {
		DataOutputStream out = null;
		DataInputStream in = null; 
		
		try {
			out = new DataOutputStream(mSocket.getOutputStream());
			in = new DataInputStream(mSocket.getInputStream());
		
		
			while(!mSocket.isClosed()) {
				String data = null;
				
				while(!mOutput.isEmpty()) {
					//Sending information to client
					data = mOutput.poll();
					out.writeUTF(data);
					out.flush();
					
					//read info from client
					data = in.readUTF();
					//Add received information from client in server array for parse
					synchronized(mServerInput) {
						mServerInput.add(new TData(mId, data));
					}
				}
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
