package task_schedule;

import com.google.gson.Gson;

import messanger.handler.Messanger;
import messanger.message.Message;

public class NewTask implements Runnable {
	private STask mTask;
	private Gson mGson;
	private Messanger mes;
	private int id;

	public NewTask(STask task, ScheduleConfig config, Messanger mes, Gson gson, int id) {
		mTask = task;
		mTask.setConfig(config);
		mGson = gson;
		this.mes = mes;
		this.id = id;
	}
	
	@Override
	public void run() {
		ScheduleSolution sol = (ScheduleSolution) mTask.solve();
		mes.send(new Message(Message.OUT_INFO, id, mGson.toJson(sol)));
	}
	
}
