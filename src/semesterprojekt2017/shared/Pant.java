package semesterprojekt2017.shared;

import java.util.Date;

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
public class Pant implements IsSerializable{

	private int idpant;
	private String panttype;
	private double value;
	private Date date;
	private int users_id;

	/**
	 * Default constructor for Pant DTO
	 */
	public Pant () {

	}

	public int getIdpant() {
		return idpant;
	}

	public void setIdpant(int idpant) {
		this.idpant = idpant;
	}

	public String getPanttype() {
		return panttype;
	}

	public void setPanttype(String panttype) {
		this.panttype = panttype;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}





}
