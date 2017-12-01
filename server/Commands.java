package server;

public interface Commands {
	
	boolean createNewTeam(String name);
	
	boolean deleteTeam(String name);
	
	boolean addUser(String name);
	
	boolean deleteUser(String name);
	
	boolean addUserInTeam(String userName, String teamName);
	
	boolean appointAsAdmin(String name, String teamName);
	
	boolean appointAsUser(String name, String teamName);
	
	boolean renameUser(String oldName, String newName);
	
}
