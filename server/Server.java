package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import data.TData;

/**
 * Class for initialization server, listener and for parsing messages between client-threads and server
 * @param - mListener - listener for accept new clients
 * @param - mClientExecutor - thread pool for client-threads
 * @param - mClients - hashmap for parse clients using id
 * @param - mOutBuffer - listener for accept new clients
 * 
 * @author ANovikov
 *
 */
public class Server extends Thread {
	private Listener mListener;
	private ExecutorService mClientExecutor;
	private HashMap<Integer, Client> mClients;
	private ArrayList<TData> mOutBuffer;
	private ArrayList<TData> mInBuffer;
	private DataOutputStream mServerOut;
	private boolean mServerStop;
	DataOutputStream stream;
	
	public Server(int port, int threadCount, OutputStream stream) {
		mClients = new HashMap<Integer, Client>();
		mClientExecutor = Executors.newFixedThreadPool(threadCount);
		mOutBuffer = new ArrayList<TData>();
		mInBuffer = new ArrayList<TData>();
		
		/*TODO
		 * Delete of rewrite this code!!!!*/
		mServerOut = new DataOutputStream(System.out);
		this.stream = new DataOutputStream(stream);
		
		mListener = new Listener(port, mClients, mClientExecutor, mInBuffer);
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			if(mServerStop) {
				System.out.println("Stop server!");
				mListener.interrupt();
				break;
			}
			
			TData data = null;
			
			synchronized(mOutBuffer) {
				if(mOutBuffer.size() != 0) {
					data = mOutBuffer.remove(0);
					Client client = mClients.get(data.mId);
					
					if(client != null) {
						client.send(data.mInfo);
					} else {
						System.out.println("Client with id " + data.mId + " not found!");
						System.out.flush();
					}
				}
			}
			
			synchronized(mInBuffer) {
				if(!mInBuffer.isEmpty()) {
					data = mInBuffer.remove(0);	
					try {
						mServerOut.writeUTF("Client " + data.mId + ": " + data.mInfo + "\n");
					} catch (IOException e) {	
						e.printStackTrace();
					}
				}
			}
		}
		
		mListener.stopListen();
		try {
			mListener.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void sendDatatoClient(int idClient, String data) {
		synchronized(mOutBuffer) {
			mOutBuffer.add(new TData(idClient, data));
		}
	}
	
	public void stopServer() {
		mServerStop = true;
	}
	

}
