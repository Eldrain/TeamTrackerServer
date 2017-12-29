package task_schedule;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

import messanger.handler.Handler;
import messanger.handler.Messanger;

public class TaskPool implements Runnable {
	private Handler mHandler;
	private Messanger mServerMessanger;
	private Stack<STask> mTaskStack;
	private Stack<Gson> mGsonStack;
	private boolean mStop;
	private ExecutorService mExecutor;
	
	public TaskPool(int count, Messanger mess) {
		mExecutor = Executors.newFixedThreadPool(count);
		mServerMessanger = mess;
		
		for(int i = 0; i < count; i++) {
			mTaskStack.push(new STask());
			mGsonStack.push(new Gson());
		}
	}
	
	private void solve(ScheduleConfig config, int id) {
		//mExecutor.execute(new NewTask());
	}

	@Override
	public void run() {
		while(!mStop) {		
			mHandler.handle();
		}	
	}
}
