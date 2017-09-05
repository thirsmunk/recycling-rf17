package semesterprojekt2017.client.rpc;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import semesterprojekt2017.shared.Pant;
import semesterprojekt2017.shared.UserDTO;

//Matches the url pattern in servlet mapping
@RemoteServiceRelativePath("rpcservice")
public interface RPCService extends RemoteService {

	/**
	 * Method for returning all the users and their information 
	 * @return an ArrayList of UserDTO objects containing all the information relevant to the admin
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public ArrayList<UserDTO> getPantUsers() throws IllegalArgumentException;

	/**
	 * Method for statistics calculation
	 * @return a value indicating the total amount of users in the system, excluding the admin (see the implementation)
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public int getAmountOfUsers() throws IllegalArgumentException;

	/**
	 * Method for statistics calculation
	 * @return a value indicating the total amount of pant collected for all users
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public double getAmountOfPant() throws IllegalArgumentException;

	/**
	 * Method for returning a single Pant User's collected pant
	 * @param id of the selected pant user
	 * @return an ArrayList containing Pant objects showing information about collected pant
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public ArrayList<Pant> getPantUser(int id) throws IllegalArgumentException;

	/**
	 * Method for checking login credentials 
	 * @param username the String entered in the username box by the guest
	 * @param password the String entered in the password box by the guest
	 * @return an object containing the user's information on succesful login or 
	 * an object with null values if the user doesn't exist
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public UserDTO authorizeUser(String username, String password) throws IllegalArgumentException;

	/**
	 * Method for adding a pant user to the database
	 * @param pDTO is the object to be added
	 * @return true or false, indicating if the addition was succesful
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public boolean addPantUser(UserDTO pDTO) throws IllegalArgumentException;

	/**
	 * Method for changing a user's password
	 * @param id of the user for identification
	 * @param newPass is the new password the user wants
	 * @return true or false, indicating if the change was succesful
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public boolean changePassword(int id, String newPass) throws IllegalArgumentException;

	/**
	 * Method for deleting a pant user
	 * @param id of the pant user to be deleted. 
	 * @return true or false, indicating if the deletion was succesful
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public boolean deletePantUser(int id) throws IllegalArgumentException;

	/**
	 * Method for the pant user to add more pant
	 * @param pDTO the pant object passed to the DAO class
	 * @return true or false, indicating if the addition of pant was succesful
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public boolean addCollectedPant(Pant pDTO) throws IllegalArgumentException;

	/**
	 * Method for deleting a pant entry 
	 * @param id of the pant-entry to be deleted
	 * @return true or false, indicating if the deletion was succesful
	 * @throws IllegalArgumentException thrown if the method is passed an illegal or inappropiate argument
	 */
	public boolean deletePantEntry(int id) throws Exception;

}
