package semesterprojekt2017.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import semesterprojekt2017.client.rpc.RPCService;
import semesterprojekt2017.shared.Pant;
import semesterprojekt2017.shared.UserDTO;



/**
 * The Data Access Layer (DAL and DAO) which is responsible for actually "reaching" into the database to retrieve data. 
 * 
 * extends RemoteServiceServlet and implements RPCService according to GWT's RPC architecture. 
 *
 */
@SuppressWarnings("serial")
public class ServiceImplDatabase extends RemoteServiceServlet implements RPCService {

	//URL, username and password for the database
	private static final String URL = "jdbc:mysql://localhost:3306/pantsystem?useSSL=false";
	private static final String USERNAME = "test";
	private static final String PASSWORD = "";

	//Initiates the connection object to be used for the connection between client and server
	private static Connection connection = null;

	/**
	 * Constructor responsible for creating connection
	 */
	public ServiceImplDatabase() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Closes the connection if an exception occurs
	 */
	private static void close() {
		try {
			connection.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns all users in the database
	 */
	@Override
	public ArrayList<UserDTO> getPantUsers() throws IllegalArgumentException {
		ArrayList<UserDTO> users = new ArrayList<UserDTO>();
		ResultSet rs = null;

		try {
			PreparedStatement getPantUsers = connection.prepareStatement("SELECT id, fullname, username, password FROM users "
					+ "WHERE type != ?");

			getPantUsers.setInt(1, 1);

			rs = getPantUsers.executeQuery();

			while(rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs.getInt("id"));
				user.setFullname(rs.getString("fullname"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));

				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * Gets a single pant user's pant collection data
	 */
	@Override
	public ArrayList<Pant> getPantUser(int id) throws IllegalArgumentException {
		ResultSet rs = null;
		ArrayList<Pant> user = new ArrayList<Pant>();

		try {
			//idpant is used in the admin menu for deletion 
			PreparedStatement getPantUser = connection.prepareStatement("SELECT idpant, panttype, value, date "
					+ "FROM pant WHERE users_id = ?");

			//The following statement sets the id in the preparedstatement, 1 refers to the 1st question mark
			getPantUser.setInt(1, id);
			rs = getPantUser.executeQuery();

			//Notice the different columns here because of the AS sql command
			while(rs.next()) {
				Pant pDTO = new Pant();

				//IDpant is used for identifying the entry deletion in admin menu
				pDTO.setIdpant(rs.getInt("idpant"));
				pDTO.setPanttype(rs.getString("panttype"));
				pDTO.setValue(rs.getInt("value"));
				pDTO.setDate(rs.getTimestamp("date"));

				user.add(pDTO);

			}

		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
				close();
			}
		}

		return user;
	}

	/**
	 * Authorizes login
	 */
	@Override
	public UserDTO authorizeUser(String username, String password) throws IllegalArgumentException {
		ResultSet rs = null;
		UserDTO pDTO = null;

		try {
			PreparedStatement authorizeUser = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");

			authorizeUser.setString(1, username);
			authorizeUser.setString(2, password);

			rs = authorizeUser.executeQuery();

			while(rs.next()) {
				pDTO = new UserDTO();
				pDTO.setId(rs.getInt("id"));
				pDTO.setFullname(rs.getString("fullname"));
				pDTO.setUsername(rs.getString("username"));
				pDTO.setPassword(rs.getString("password"));
				pDTO.setType(rs.getInt("type"));


			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();	
			}catch(SQLException ex) {
				ex.printStackTrace();
				close();
			}
		}
		return pDTO;
	}

	/**
	 * Creates a new user
	 */
	@Override
	public boolean addPantUser(UserDTO pDTO) throws IllegalArgumentException {

		try {
			PreparedStatement addPantUser = connection.prepareStatement("INSERT INTO users (fullname, username, password) "
					+ "VALUES (?, ?, ?)");

			addPantUser.setString(1, pDTO.getFullname());
			addPantUser.setString(2, pDTO.getUsername());
			addPantUser.setString(3, pDTO.getPassword());
			//executeUpdate is used since this isn't a SELECT statement, but rather an update/insertion
			int countOfRows = addPantUser.executeUpdate();

			//Since an insertion of one user must mean an increase of one row we rely on logic to return a boolean value
			if(countOfRows == 1) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Changes the user's password
	 */
	@Override
	public boolean changePassword(int id, String password) throws IllegalArgumentException {

		try {
			PreparedStatement changePass = connection.prepareStatement("UPDATE users SET password = ? WHERE id = ?");

			changePass.setString(1, password);
			changePass.setInt(2, id);

			int countOfRows = changePass.executeUpdate();

			if(countOfRows == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Deletes a user
	 */
	@Override
	public boolean deletePantUser(int id) throws IllegalArgumentException {

		try {
			PreparedStatement deletePantUser = connection.prepareStatement("DELETE FROM users WHERE id = ?");

			deletePantUser.setInt(1, id);

			int countOfRows = deletePantUser.executeUpdate();

			if(countOfRows == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Deletes a pant entry
	 */
	@Override
	public boolean deletePantEntry(int id) throws IllegalArgumentException {

		try {
			PreparedStatement deletePantEntry = connection.prepareStatement("DELETE FROM pant WHERE idpant = ?");

			deletePantEntry.setInt(1, id);

			int countOfRows = deletePantEntry.executeUpdate();

			if(countOfRows == 1) {
				return true;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * Adds a pant entry
	 */
	@Override
	public boolean addCollectedPant(Pant pDTO) throws IllegalArgumentException {

		try {
			PreparedStatement addCollectedPant = connection.prepareStatement("INSERT INTO pant "
					+ "(panttype, value, users_id) VALUES (?, ?, ?)");

			addCollectedPant.setString(1, pDTO.getPanttype());
			addCollectedPant.setDouble(2, pDTO.getValue());
			addCollectedPant.setInt(3, pDTO.getUsers_id());

			int rowsAffected = addCollectedPant.executeUpdate();

			if(rowsAffected == 1) {
				return true;
			}


		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Gets amount of users in the system that aren't type 1 (admin)
	 */
	@Override
	public int getAmountOfUsers() throws IllegalArgumentException {
		ResultSet rs = null;
		int amountOfUsers = 0;

		try {
			PreparedStatement getAmountOfUsers = connection.prepareStatement("SELECT username FROM users WHERE type != 1");

			rs = getAmountOfUsers.executeQuery();

			//As long as there is a new row in the ResultSet, it must mean there's an user
			while(rs.next()) {
				amountOfUsers++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
				close();
			}
		}
		return amountOfUsers;
	}

	/**
	 * Gets total amount of pant collected
	 */
	@Override
	public double getAmountOfPant() throws IllegalArgumentException {
		ResultSet rs = null;
		double totalPant = 0;

		try {
			PreparedStatement getAmountOfPant = connection.prepareStatement("SELECT value FROM pant");

			rs = getAmountOfPant.executeQuery();

			//Arithmetic operation for each row in the ResultSet. 
			while(rs.next()) {
				totalPant += rs.getDouble("value");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch(SQLException ex) {
				ex.printStackTrace();
				close();
			}
		}
		return totalPant;
	}


}
