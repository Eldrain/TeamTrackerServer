package task_schedule;

import task_framework.ATask;
import task_framework.ISolution;

public class STask extends ATask {
	static {
		//String property = System.getProperty("java.library.path");
		System.loadLibrary("SxheduleDLL");
	}

	public STask() {
		super(getProtectionTask());
		mConfig = new ScheduleConfig();
	}

	@Override
	public ISolution solve() {
		if(mConfig == null)
			return null;
		return solveTask((ScheduleConfig)mConfig);
	}
	
	native private static ScheduleProtection getProtectionTask();
	
	native private ScheduleSolution solveTask(ScheduleConfig config);

}
