package task_schedule;

import task_framework.IConfig;
import task_framework.IProtection;

public class ScheduleProtection implements IProtection {
	private int mMaxJ;
	private int mMaxPr;
	private int mCountMethods;
	
	public ScheduleProtection(int maxJ, int maxPr, int metCount) {
		mMaxJ = maxJ;
		mMaxPr = maxPr;
		mCountMethods = metCount;
	}
	
	@Override
	public boolean checkConfig(IConfig config) {
		ScheduleConfig c = (ScheduleConfig) config;
		
		if(c.mCountJobs > mMaxJ) {
			System.out.println("Schedule Configuration checking error: count of jobs excess the permissible value!");
			return false;
		}
		if(c.mCountProcs > mMaxPr) {
			System.out.println("Schedule Configuration checking error: count of processors excess the permissible value!");
			return false;
		}
		if(c.mMethod >= mCountMethods) {
			System.out.println("\"Schedule Configuration checking error: index of method excess the permissible value!");
			return false;
		}
			
		return true;
	}

	@Override
	public IConfig getStandartConfig() {
		ScheduleConfig c = new ScheduleConfig();
		
		c.mMethod = mCountMethods - 1;
		c.mCountJobs = mMaxJ;
		c.mCountProcs = mMaxPr;
		c.mRelations = null;
		c.mTimes = null;
		return c;
	}
}