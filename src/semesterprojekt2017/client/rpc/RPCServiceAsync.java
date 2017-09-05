package semesterprojekt2017.client.rpc;

import java.util.ArrayList;


import com.google.gwt.user.client.rpc.AsyncCallback;

import semesterprojekt2017.shared.Pant;
import semesterprojekt2017.shared.UserDTO;

/**
 * Used for calling the server to retrieve information and the server also uses this class (by the asyncCallback object) 
 * to call the client back once it has an answer to the request. 
 * 
 * All methods in this class are void, since the client shouldn't expect an answer instantly 
 *
 */

public interface RPCServiceAsync {

	public void getPantUsers(AsyncCallback<ArrayList<UserDTO>> asyncCallback) throws IllegalArgumentException;

	public void getAmountOfUsers(AsyncCallback<Integer> asyncCallback) throws IllegalArgumentException;

	public void getAmountOfPant(AsyncCallback<Double> asyncCallback) throws IllegalArgumentException;

	public void getPantUser(int id, AsyncCallback<ArrayList<Pant>> asyncCallback) throws IllegalArgumentException;

	public void authorizeUser(String username, String password, AsyncCallback<UserDTO> asyncCallback) throws IllegalArgumentException;

	public void addPantUser(UserDTO puDTO, AsyncCallback<Boolean> asyncCallback) throws IllegalArgumentException;

	public void changePassword(int id, String newPass, AsyncCallback<Boolean> asyncCallback) throws IllegalArgumentException;

	public void deletePantEntry(int id, AsyncCallback<Boolean> asyncCallback) throws IllegalArgumentException;

	public void deletePantUser(int id, AsyncCallback<Boolean> asyncCallback) throws IllegalArgumentException;

	public void addCollectedPant(Pant pDTO, AsyncCallback<Boolean> asyncCallback) throws IllegalArgumentException;

}
