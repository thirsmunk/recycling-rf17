package semesterprojekt2017.client.logic;

import java.util.ArrayList;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.ListDataProvider;

import semesterprojekt2017.client.rpc.RPCServiceAsync;
import semesterprojekt2017.client.ui.ContentPanel;
import semesterprojekt2017.client.ui.admin.adminMainView.AdminMainView;
import semesterprojekt2017.shared.Pant;
import semesterprojekt2017.shared.UserDTO;

/**
 * One of three AppControllers. 
 * This AppController takes care of all the logic for the admin's input
 * Built very similarly to the PantController (also an AppController)
 *
 */
public class AdminController {

	private ContentPanel contentPanel;
	private RPCServiceAsync rpcService;
	private AdminMainView adminMainView;
	private UserDTO adminUser;

	private double valueOfSinglePant;
	private double totalUsers;
	private double totalPant;
	private double averagePant;

	//ListDataProvider containing all the users in the system 
	private ListDataProvider<UserDTO> listProviderUsers;

	//ListDataProvider containing all the pant for the selected user
	private ListDataProvider<Pant> listProviderPant;

	/**
	 * One of the three AppControllers, this one controlling all of the logic while the admin is logged in
	 * @param contentPanel the object of class ContentPanel, which is initialized in the MainController
	 * @param rpcService the proxy of class RPCServiceAsync, which is created in the MainController 
	 */
	public AdminController(ContentPanel contentPanel, RPCServiceAsync rpcService) {
		this.adminMainView = contentPanel.getAdminView();
		this.rpcService = rpcService;
		this.contentPanel = contentPanel;

		bindHandlers();

		listProviderUsers = new ListDataProvider<>();
		listProviderPant = new ListDataProvider<>();

		//Sends the listProviderUsers to the initPantInformationTable method to initialize the table (show it)
		//The listProviderUsers is filled with data in the loadAdmin method
		adminMainView.getPantInfoView().initPantInformationTable(listProviderUsers);

		//Same as above, initializes the table for viewing a single user's collected pant 
		adminMainView.getSinglePantView().initSinglePantViewTable(listProviderPant);

	}

	/**
	 * The method responsible for adding the logic to the Widgets (buttons) and ActionCells
	 * All the logic handlings events is specified by us through @Override on the ClickHandler & ActionCell.Delegate
	 *  interfaces
	 */
	private void bindHandlers() {

		adminMainView.addClickHandlers(new AdminMenuClickHandler());
		adminMainView.getPantInfoView().addClickHandlerView(new ViewPantClickHandler());
		adminMainView.getPantInfoView().addClickHandlerDeleteUser(new DeleteUserClickHandler());
		adminMainView.getSinglePantView().addClickHandlers(new SinglePantBackClickHandler());
		adminMainView.getSinglePantView().addDeletePantClickHandler(new DeletePantClickHandler());
		adminMainView.getChangePassView().getChangePasswordBtn().addClickHandler(new ChangePasswordClickHandler());

	}

	/**
	 * Assigns the admin to the adminDTO reference, loads the tables with data and retrieves the initial statistics from the server
	 * @param adminDTO the object of class UserDTO which is returned from the server upon succesful login
	 */
	public void loadAdmin(UserDTO adminDTO) {
		this.adminUser = adminDTO;

		loadTable();
		loadStatistics();

	}

	/**
	 * This class implements the ClickHandler interface and overrides the onClick function 
	 * to apply our own logic. This class is used for all the menu buttons, as the name suggests. 
	 *
	 */
	private class AdminMenuClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if(event.getSource() == adminMainView.getStatsBtn()){
				refreshStatistics();
				adminMainView.changeView(adminMainView.getStatistikView());
			}

			else if(event.getSource() == adminMainView.getInfoBtn()){
				//Makes sure the single pant view is shown when the menu is navigated without selecting "Back" in this view
				if(listProviderPant.getList().isEmpty()) {
					adminMainView.changeView(adminMainView.getPantInfoView());
				}
				else
					adminMainView.changeView(adminMainView.getSinglePantView());
			}
			else if(event.getSource() == adminMainView.getChangePasswordBtn()){
				adminMainView.changeView(adminMainView.getChangePassView());
			}
			else if(event.getSource() == adminMainView.getLogoutBtn()) {
				listProviderUsers.getList().clear();
				adminMainView.getStatistikView().clearStatistics();

				//Condition checking if the single pant list should be cleared 
				if(!listProviderPant.getList().isEmpty()) {
					listProviderPant.getList().clear();
				}
				//Switches to the login-view
				contentPanel.changePanel(contentPanel.getLoginView());

				//Switches the view in the deck "behind-the-scenes", so the pant-info is shown the next time 
				//the admin logs in
				adminMainView.changeView(adminMainView.getPantInfoView());
			}
		}

	}

	/**
	 * This class implements the ClickHandler interface and specifies the logic for when the back-button
	 * is clicked in the SinglePantView
	 *
	 */
	private class SinglePantBackClickHandler implements ClickHandler {

		//Clears the Pant List, defaults the label and variable to null/0 and reverts back to the overview
		@Override
		public void onClick(ClickEvent event) {
			adminMainView.changeView(adminMainView.getPantInfoView());
			listProviderPant.getList().clear();
			adminMainView.getSinglePantView().getPantCollectedLbl().setValue(null);
			valueOfSinglePant = 0;

		}

	}

	/**
	 * This class implements the ActionCell.Delegate interface and specifies the logic for when the Delete User
	 * ActionCell is clicked
	 *
	 */
	private class DeleteUserClickHandler implements ActionCell.Delegate<UserDTO> {

		@Override
		public void execute(final UserDTO user) {
			//Confirms that the user is to be deleted
			boolean confirmDeletion = Window.confirm("Are you sure you want to delete " + user.getFullname());

			if(confirmDeletion) {
				rpcService.deletePantUser(user.getId(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Server message: Error");

					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							//Removes the user from the CellTable
							listProviderUsers.getList().remove(user);
						} else {
							Window.alert("Could not delete user");
						}


					}

				});
			}

		}

	}

	/**
	 * Class implementing the ActionCell.Delegate interface of type UserDTO. 
	 * Overrides the execute(object) function declaring our own logic behind execution of the MouseUp event
	 *
	 */
	private class ViewPantClickHandler implements ActionCell.Delegate<UserDTO> {

		@Override
		public void execute(UserDTO user) {

			//Executes the RPC call 
			rpcService.getPantUser(user.getId(), new AsyncCallback<ArrayList<Pant>>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Couldn't retrieve pant from the user");

				}

				@Override
				public void onSuccess(ArrayList<Pant> result) {

					//Inserts the data from ArrayList into the ListDataProvider 
					listProviderPant.getList().addAll(result);

					//Enhanced for-loop which iterates through the Pant ArrayList and adds the sum 
					//to the "Total Collected Pant" label in the view
					for(Pant p: result) {
						valueOfSinglePant += p.getValue();
					}

					adminMainView.getSinglePantView().getPantCollectedLbl().setValue(valueOfSinglePant);

					//Switches to the view showing the table for the selected pant user
					adminMainView.changeView(adminMainView.getSinglePantView());
				}


			});


		}

	}

	/**
	 * Class implementing the ActionCell.Delegate interface of type Pant. 
	 * Overrides the execute(object) function declaring our own logic behind execution of the MouseUp event
	 *
	 */
	private class DeletePantClickHandler implements ActionCell.Delegate<Pant> {

		@Override
		public void execute(final Pant pant) {
			//Same principle as in delete pant user
			boolean confirmDeletion = Window.confirm("Are you sure you want to delete the following entry:\n"
					+ "Pant Type: " + pant.getPanttype() + "\n" + "Pant Value: " + pant.getValue() + "\n" 
					+ "Pant Date: " + pant.getDate());

			if(confirmDeletion) {

				rpcService.deletePantEntry(pant.getIdpant(), new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error response from server");

					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							Window.alert("Pant entry deleted succesfully");
							//Removes the object from the list when the server has deleted the entry succesfully
							listProviderPant.getList().remove(pant);
							//Subtracts the deleted value from the "Total Collected Pant" label
							adminMainView.getSinglePantView().getPantCollectedLbl().setValue(valueOfSinglePant-pant.getValue());
						} else
							Window.alert("Couldn't delete the pant entry");

					}

				});
			}


		}

	}

	/**
	 * This method is responsible for loading the ListDataProvider containing all the users with data 
	 * from the server. The data is retrieved using a RPC-call.  
	 */
	private void loadTable() {

		rpcService.getPantUsers(new AsyncCallback<ArrayList<UserDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Could not load users");

			}

			@Override
			public void onSuccess(ArrayList<UserDTO> result) {
				listProviderUsers.getList().addAll(result);

			}

		});
	}

	/**
	 * Class implementing the ClickHandler interface. 
	 * Overrides onClick function declaring our own logic behind execution of the MouseUp event.
	 * @author Lasse Munk
	 *
	 */
	private class ChangePasswordClickHandler implements ClickHandler {

		private String password;

		@Override
		public void onClick(ClickEvent event) {
			//Checks whether the old password correlates with the user's password before moving on
			if(adminUser.getPassword() == adminMainView.getChangePassView().getOldPasswordTextBox().getText()) {

				password = adminMainView.getChangePassView().getNewPasswordTextBox().getText();

				rpcService.changePassword(adminUser.getId(), password, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Couldn't change password");

					}

					@Override
					public void onSuccess(Boolean result) {
						if(result) {
							Window.alert("Password changed!");
							adminUser.setPassword(password);

						} else
							Window.alert("Couldn't change password...");

						adminMainView.getChangePassView().clearTextBoxFields();

					}

				});
			} else {
				adminMainView.getChangePassView().clearTextBoxFields();
			}



		}

	}

	/**
	 * 
	 * Method responsible for loading the statistics. Makes use of a RPC-call to retrieve the relevant information. Furthermore, 
	 * client-side arithmetic operations are used to calculate an average value. 
	 * A quirk with this method is the use of a RPC-call inside another RPC call's onSucces. 
	 * 
	 */
	private void loadStatistics() {

		rpcService.getAmountOfUsers(new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Could not retrieve amount of users");

			}

			@Override
			public void onSuccess(Integer result) {

				adminMainView.getStatistikView().getNumberOfUsersLbl().setValue(result);
				totalUsers = result;

				rpcService.getAmountOfPant(new AsyncCallback<Double>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Could not retrieve amount of pant");

					}

					@Override
					public void onSuccess(Double result) {
						adminMainView.getStatistikView().getTotalCollectedLbl().setValue(result);
						totalPant = result;
						averagePant = totalPant/totalUsers;
						adminMainView.getStatistikView().getAverageCollectedLbl().setValue(averagePant);

					}

				});

			}

		});

	}

	/**
	 * Method for updating the statistics whenever the admin clicks the Statistic button in the menu
	 */
	private void refreshStatistics() {

		adminMainView.getStatistikView().clearStatistics();
		loadStatistics();

	}




}
