

import java.util.Scanner;

import com.google.gson.Gson;

import data.Coordinate;
import server.Database;
import server.Server;
import task_framework.ITask;
import task_schedule.STask;
import task_schedule.ScheduleConfig;
import task_schedule.ScheduleProtection;
import task_schedule.ScheduleSolution;

public class TeamTrackerServer {
	public static final int PORT = 2222;
	public static final String dbUrl = "jdbc:mysql://localhost:3306/test";
	public static final String userName = "root";
	public static final String userPassword = "root";
	
	public static void main(String[] args) {
		//Database db = new Database(dbUrl, userName, userPassword);
		
		
		Server server = new Server(PORT, 2, System.out);
		String data = "";
		Scanner scanner = new Scanner(System.in);
		
		Gson gson = new Gson();
		Coordinate pack = new Coordinate(30, 500);
		ITask task = new STask();
		//ScheduleProtection protect = (ScheduleProtection) task.getProtection();
		
		ScheduleConfig config = (ScheduleConfig) task.getStandartConfig();
		config.mCountProcs = 3;
		config.mMethod = 3;
		
		task.setConfig(config);
		
		while(!data.equals("quit")) {	
			System.out.println("Enter message for client: ");
			data = gson.toJson(pack);			
			pack.mX++;
			pack.mY++;
			System.out.println("Enter id of client: ");
			int id = scanner.nextInt();
			
			//task.solve();
			
		}
		//server.sendDatatoClient(0, );
		
		scanner.close();
		server.stopServer();
	}

}
