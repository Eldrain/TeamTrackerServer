package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.google.gson.Gson;
import messanger.handler.Handler;
import messanger.handler.Messanger;
import messanger.message.Message;
import task_schedule.STask;
import task_schedule.ScheduleConfig;
import task_schedule.ScheduleProtection;
import task_schedule.ScheduleSolution;

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
	private HashMap<Integer, Messanger> mClients;
	//private TaskSolver mSolver;
	private Handler mHandler;
	
	private DataOutputStream mServerOut;
	
	
	private boolean mServerStop;
	
	private STask mTask;
	private Gson mGson;
	
	public Server(int port, int threadCount, OutputStream outStream) {
		mClients = new HashMap<Integer, Messanger>();
		mClientExecutor = Executors.newFixedThreadPool(threadCount);
		mServerOut = new DataOutputStream(System.out);
		
		mHandler = new Handler() {
			@Override
			public void handle(Message message) {
				try {
					switch(message.code) {
					case Message.OUT_INFO:
						Messanger clientMes = null;
						clientMes = mClients.get(message.client);
						clientMes.send(message);
						break;
					case Message.IN_NEW_CLIENT:
						
							mServerOut.writeUTF("\nNew client is connected (" + message.client + ": " + message.info + ")");
						
						mServerOut.flush();
						break;
					case Message.IN_NEW_TASK:
						ScheduleConfig config = mGson.fromJson(message.info, ScheduleConfig.class);
						mTask.setConfig(config);
						
						ScheduleSolution sol = (ScheduleSolution) mTask.solve();
						sol.print();
						message.code = Message.OUT_INFO;
						message.info = mGson.toJson(sol);
						
						mClients.get(message.client).send(message);
						mServerOut.writeUTF("\nTask is solved.");
						mServerOut.flush();
						break;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
		};	
		
		mTask = new STask();
		mGson = new Gson();
		
		mListener = new Listener(port, mClients, mClientExecutor, mHandler, mGson.toJson((ScheduleProtection)mTask.getProtection()));
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			if(mServerStop) {
				System.out.println("Stop server!");
				mListener.stopListen();
				try {
					mListener.interrupt();
					mListener.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			}
			
			mHandler.handle();
		}		
	}
	
	public void stopServer() {
		mServerStop = true;
	}
}