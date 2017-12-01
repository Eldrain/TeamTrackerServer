package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for working with database
 * @param mCon - object of connection with database
 * @author ANovikov
 *
 */
public class Database implements IDatabase, Commands {
	private Connection mCon;
	private Statement mStmt;
	private ResultSet mResult;
	
	/**
	 * Standard constructor for initialization a database
	 * @param url - url for connecting to database
	 * @param user - Username of database
	 * @param password - password for user
	 */
	public Database(String url, String user, String password) {
		try {
			//try get connection
			mCon = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			System.out.println("Connection sql database error!");
			e.printStackTrace();
			return;
		}
		
		try {
			mStmt = mCon.createStatement();
		} catch (SQLException e) {
			System.out.println("CreateStatement error.");
			e.printStackTrace();
		}
	}

	@Override
	public void createDatabase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteDatabase() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeConnection() {
		try {
			mCon.close();
		} catch (SQLException e) {
			System.out.println("Closing sql connection error.");
			e.printStackTrace();
		}
		try {
			mStmt.close();
		} catch (SQLException e) {
			System.out.println("Closing sql statement error.");
			e.printStackTrace();
		}
		
	}

	@Override
	public boolean createNewTeam(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteTeam(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addUser(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addUserInTeam(String userName, String teamName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean appointAsAdmin(String name, String teamName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean appointAsUser(String name, String teamName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean renameUser(String oldName, String newName) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
