package server;

public interface IDatabase {
	
	void createDatabase();
	
	void deleteDatabase();
	
	void closeConnection();
}
