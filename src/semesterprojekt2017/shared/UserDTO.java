package semesterprojekt2017.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A shared class used by the server and client. 
 * To be recognized as a DTO it follows the convention of:
 * 1) Implementing the Serializable interface
 * 2) Providing a default constructor
 * 3) Is in the shared folder
 * @author Lasse Munk
 *
 */
public class UserDTO implements IsSerializable {

	private int id;
	private String fullname;
	private String username;
	private String password;
	private int type;

	/**
	 * Constructor for UserDTO with parameters
	 * @param fullname the full name entered by the user
	 * @param username the username entered by the user
	 * @param password the password entered by the user 
	 */
	public UserDTO(String fullname, String username, String password) {
		this.fullname = fullname;
		this.username = username;
		this.password = password;
	}

	/**
	 * Default constructor for User DTO
	 */
	public UserDTO() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


}
