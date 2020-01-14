package application;

//Player class
public class Player {
	//Declaring class variables
	String username,password;
	boolean []levelCompleted;
	 /**
	  * Constructor that allows them to store the user name, password, and completed levels
	  * @param u - String representing the user name
	  * @param p - String representing the password
	  * @param level - Boolean array representing how many levels they have finished
	  */
	public Player(String u,  String p, boolean[]level) {
		username =u;
		levelCompleted = level;
		password = p;
	}
	/**
	 * Method that obtains an array of booleans representing their progress
	 * @return boolean[] representing the levels completed
	 */
	public boolean[] getLevelCompleted() {
		return levelCompleted;
	}
/**
 * Method that allows to set the levels completed
 * @param levelCompleted - boolean array representing the desired levels completed
 */
	public void setLevelCompleted(boolean[] levelCompleted) {
		this.levelCompleted = levelCompleted;
	}
/**
 * Method to obtain the user name
 * @return -String representing the user name
 */
	public String getUsername() {
		return username;
	}
/**
 * Method to set user name
 * @param username - String representing the username
 */
	public void setUsername(String username) {
		this.username = username;
	}
/**
 * Method to obtain password
 * @return String representing password
 */
	public String getPassword() {
		return password;
	}
/**
 * Method to set the password
 * @param password - String representing desired password
 */
	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
