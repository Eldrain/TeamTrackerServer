package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

import javax.net.ServerSocketFactory;

import data.TData;

/**
 * Class Listener create server socket and accept new connections
 * @author ANovikov
 *
 */
public class Listener extends Thread {
	private ServerSocket mServerSocket;
	private final int PORT;
	private boolean mStopListen;
	private HashMap<Integer, Client> mClients;
	private ExecutorService mClientExecutor;
	private ArrayList<TData> mServerInput;
	private int count;
	
	public Listener(int port, HashMap<Integer, Client> clients, ExecutorService executor, ArrayList<TData> serverInput) {
		PORT = port;
		mClients = clients;
		mClientExecutor = executor;
		mServerInput = serverInput;
		this.start();
	}
	
	@Override
	public void run() {
		mServerSocket = null;
		try {
			mServerSocket = ServerSocketFactory.getDefault().createServerSocket(PORT);//factory.createServerSocket(port)
			System.out.println("Address: " + mServerSocket.getInetAddress());
			System.out.println("Waiting for client connected.");
			
			while(!mStopListen) {
				Socket client = mServerSocket.accept();
				System.out.println("New client accepted with id = " + count);
				
				Client newClient = new Client(client, count, mServerInput);				
				mClients.put(count, newClient);
				mClientExecutor.execute(newClient);
				count++;
			}
			
		} catch(SocketException e) {
			if(mStopListen)
				System.out.println("ServerSocket is close. Stop listening clients.");
			else
				System.out.println("PORT is busy!");
		} catch (IOException e) {
			System.out.print("IOException in Listener!!!");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Listener stopped.");
	}
	
	public void stopListen() {
		mStopListen = true;
		try {
			mServerSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}