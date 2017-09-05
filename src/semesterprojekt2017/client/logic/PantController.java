package semesterprojekt2017.client.logic;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

import semesterprojekt2017.client.rpc.RPCServiceAsync;
import semesterprojekt2017.client.ui.ContentPanel;
import semesterprojekt2017.client.ui.pant.pantMainView.PantMainView;
import semesterprojekt2017.shared.Pant;
import semesterprojekt2017.shared.UserDTO;

/**
 * AppController handling the logic for all activities while a Pant User is logged in
 *
 */
public class PantController {

	//Declared variables to be used in the class 
	private ContentPanel content;
	private RPCServiceAsync rpcService;
	private PantMainView pantMainView;
	private UserDTO currentUser;
	private Pant pDTO;
	private double valueOfPant;

	//List Data Provider which contains the data of the logged in user's collected pant
	private ListDataProvider<Pant> listProviderPant;


	/**
	 * Constructor taking two arguments; an object from the ContentPanel and RPCServiceAsync class. Furthermore, 
	 * variables declared in the class' first lines are set or initialized here. 
	 * 
	 * A ListDataProvider containing type Pant objects is initialized and a public method is called from another class
	 * to initialize the table with columns and showing data. 
	 * 
	 * The bindHandlers() method is also called here, which has exactly the same function as in MainController; 
	 * binding logic to the events. 
	 * @param content an object of class ContentPanel which is initialized in MainController
	 * @param rpcService the service proxy, which is initialized in MainController
	 */
	public PantController(ContentPanel content, RPCServiceAsync rpcService) {
		this.content = content;
		this.rpcService = rpcService;
		this.pantMainView = content.getPantView();

		listProviderPant = new ListDataProvider<>();
		pantMainView.getPantUserView().initPantTable(listProviderPant);
		bindHandlers();
	}

	/**
	 * Assigns the logged in user to the currentPDTO reference and loads the table with data
	 * This method is used in the maincontroller when a pantuser is recognized at login
	 * 
	 * @param currentPDTO the argument which the method takes. This object's reference is transferred to the currentUser
	 * object in the PantController class for later use. 
	 */
	public void loadPantUser(UserDTO currentPDTO) {
		this.currentUser = currentPDTO;
		loadTables();
	}

	/**
	 * Same principle as the MainController's bindHandlers method. Implementing the logic when an event arises.
	 */
	private void bindHandlers() {
		pantMainView.addClickHandlers(new PantMenuClickHandler());
		pantMainView.getPantUserView().addClickHandlers(new PantSubmitClickHandler());
		pantMainView.getChangePassView().addClickHandlers(new ChangePasswordClickHandler());
	}

	/**
	 * Defines the logic behind the menu buttons while a pantuser is logged in. 
	 *
	 */
	private class PantMenuClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(event.getSource() == pantMainView.getPantProfileBtn()) {
				pantMainView.changeView(pantMainView.getPantUserView());
			}

			else if(event.getSource() == pantMainView.getPantChangePasswordBtn()) {
				pantMainView.changeView(pantMainView.getChangePassView());
			}

			else if(event.getSource() == pantMainView.getPantLogoutBtn()) {
				listProviderPant.getList().clear();
				currentUser = null;
				valueOfPant = 0.0;
				pantMainView.getPantUserView().getTotalPantCollectedLbl().setValue(valueOfPant);
				content.changePanel(content.getLoginView());
				pantMainView.changeView(pantMainView.getPantUserView());
			}

		}

	}

	/**
	 * Handles the logic behind submitting pant to the system. The overriden onClick method checks 
	 * whether the user has input A, B or C or even if it is not a valid input.
	 *
	 */
	private class PantSubmitClickHandler implements ClickHandler {

		//Final values are initialized for calculating the value of the collected pant 
		private final int valueA = 1;
		private final double valueB = 1.5;
		private final int valueC = 3;

		private String panttype;
		private int pantamount;
		private double pantvalue;

		@Override
		public void onClick(ClickEvent event) {

			if(pantMainView.getPantUserView().getARadioBtn().getValue() == true) {
				panttype = "A";
			} else if (pantMainView.getPantUserView().getBRadioBtn().getValue() == true) {
				panttype = "B";
			} else if (pantMainView.getPantUserView().getCRadioBtn().getValue() == true) {
				panttype = "C";
			} else {
				Window.alert("You need to pick either A, B or C before submitting your pant!");
				return;
			}

			//Initializes the object to be sent
			pDTO = new Pant();

			//Initializes an object of the Date class representing a specific instant of time 
			java.util.Date date = new java.util.Date();

			pantamount = pantMainView.getPantUserView().getPantValueBox().getValue();


			//Switch determining the value of the pant entered
			switch(panttype.charAt(0)) {
			case 'A':	
				pantvalue = pantamount*valueA;
				break;
			case 'B':
				pantvalue = pantamount*valueB;
				break;
			case 'C':
				pantvalue = pantamount*valueC;
				break;
			}		

			//Assigns the values to the objects before sending it off
			pDTO.setPanttype(panttype);
			pDTO.setValue(pantvalue);
			pDTO.setUsers_id(currentUser.getId());
			pDTO.setDate(date);

			rpcService.addCollectedPant(pDTO, new AsyncCallback<Boolean>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Something went wrong, did you enter the correct information?");
					pantMainView.getPantUserView().clearTextBoxFields();

				}

				@Override
				public void onSuccess(Boolean result) {
					if(result) {
						Window.alert("Your collected pant has been added!");

						//Adds the new object to the table and the table refreshes automatically 
						listProviderPant.getList().add(pDTO);

						//Adds the new pant entry to the total value, so the user doesn't have to refresh
						valueOfPant += pDTO.getValue();
						pantMainView.getPantUserView().getTotalPantCollectedLbl().setValue(valueOfPant);

						//Clears the textbox and radio buttons 
						pantMainView.getPantUserView().clearTextBoxFields();
					}

					else Window.alert("Could not add pant");


				}

			});
		}

	}

	/**
	 * Method responsible for loading the tables for the logged in pantuser. The loadPantUser(UserDTO) method assigns 
	 * a value to the currentUser object, which is used in this method's RPC-call. 
	 */
	private void loadTables() {


		rpcService.getPantUser(currentUser.getId(), new AsyncCallback<ArrayList<Pant>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Oops, something went wrong!");

			}

			@Override
			public void onSuccess(ArrayList<Pant> pantData) {
				//Adds the data to the ListDataProvider object
				listProviderPant.getList().addAll(pantData);

				//For-loop iterating through all the values to get the user's total pant value
				for(Pant list: pantData) {
					valueOfPant += list.getValue();
				}

				pantMainView.getPantUserView().getTotalPantCollectedLbl().setValue(valueOfPant);
			}

		});
	}

	/**
	 * Handles the logic behind password change. Implements the ClickHandler class, overriding the onClick method.
	 *
	 */
	private class ChangePasswordClickHandler implements ClickHandler {
		private String password;

		@Override
		public void onClick(ClickEvent event) {
			//Checks whether the old password correlates with the user's password before moving on
			if(currentUser.getPassword() == pantMainView.getChangePassView().getOldPasswordTextBox().getText()) {

				password = pantMainView.getChangePassView().getNewPasswordTextBox().getText();

				rpcService.changePassword(currentUser.getId(), password, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Couldn't change password");

					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							Window.alert("Password changed!");
							currentUser.setPassword(password);
							pantMainView.getChangePassView().clearTextBoxFields();

						} else
							Window.alert("Couldn't change password...");

						pantMainView.getChangePassView().clearTextBoxFields();

					}

				});
			} else {
				Window.alert("The entered password is incorrect");
			}



		}

	}

}
