package semesterprojekt2017.client.logic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import semesterprojekt2017.client.rpc.RPCService;
import semesterprojekt2017.client.rpc.RPCServiceAsync;
import semesterprojekt2017.client.ui.ContentPanel;
import semesterprojekt2017.shared.UserDTO;

/**
 * The AppController which is instantiated in the onModuleLoad method.
 *
 */
public class MainController {

	private ContentPanel contentPanel;
	private RPCServiceAsync rpcService;

	//The other controllers are declared here
	private AdminController adminController;
	private PantController pantController;

	/**
	 * The constructor which is used for initialization. Stands apart from the other two AppControllers, 
	 * as it takes no arguments. The ContentPanel and proxy are created here and passed to the other controllers. 
	 */
	public MainController() {

		//Initializes the "main" panel to be added to the RootLayoutPanel
		contentPanel = new ContentPanel();

		//The "main" panel is added
		RootLayoutPanel.get().add(contentPanel);

		//An instance of the service proxy class is created
		rpcService = GWT.create(RPCService.class);

		//The other controllers are initialized here
		adminController = new AdminController(contentPanel, rpcService);
		pantController = new PantController(contentPanel, rpcService);

		//The bindHandlers() method is run here to add ClickHandlers to the buttons in the loginView and createView panels
		bindHandlers();

	}

	/**
	 * Adds the logic to the events originating from "clicks" on the buttons
	 */
	private void bindHandlers() {
		contentPanel.getLoginView().addClickHandlers(new LoginClickHandler());
		contentPanel.getCreateView().addClickHandlers(new CreateClickHandler());
	}

	/** Checks whether the event originates from a click on the loginBtn or the createUserBtn and acts 
	 * accordingly 
	 */
	private class LoginClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(event.getSource() == contentPanel.getLoginView().getLoginBtn()) {
				//Retrieves information from the username and password box
				String username = contentPanel.getLoginView().getUsernameTextBox().getText();
				String password = contentPanel.getLoginView().getPasswordTextBox().getText();

				rpcService.authorizeUser(username, password, new AsyncCallback<UserDTO>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Username or password incorrect");

					}

					@Override
					public void onSuccess(UserDTO pDTO) {
						//If a user can't be found in the database
						if(pDTO == null) {
							Window.alert("Sorry, username or password is incorrect");
						} else {

							//Removes the text in the boxes on a succesful login
							contentPanel.getLoginView().clearTextBoxFields();

							//Checks whether the logged-in user is an user or an admin
							if(pDTO.getType() != 1) {
								pantController.loadPantUser(pDTO);
								contentPanel.changePanel(contentPanel.getPantView());
							} else {

								adminController.loadAdmin(pDTO);
								contentPanel.changePanel(contentPanel.getAdminView());
							}
						}


					}

				});

			}

			else if(event.getSource() == contentPanel.getLoginView().getCreateBtn()) {
				contentPanel.changePanel(contentPanel.getCreateView());
			}


		}

	}

	/**
	 * Handles the logic behind creating a new user in the system. 
	 *
	 */
	private class CreateClickHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			if(event.getSource() == contentPanel.getCreateView().getCreateOK()) {

				//Retrieves information from the three textboxes
				String fullname = contentPanel.getCreateView().getCreateFullname().getText();
				String username = contentPanel.getCreateView().getCreateUsername().getText();
				String password = contentPanel.getCreateView().getCreateUserPass().getText();

				UserDTO pDTO = new UserDTO();
				pDTO.setFullname(fullname);
				pDTO.setUsername(username);
				pDTO.setPassword(password);

				rpcService.addPantUser(pDTO, new AsyncCallback<Boolean>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Something went wrong, please try again.");

					}

					@Override
					public void onSuccess(Boolean result) {
						if(!result) {
							Window.alert("The user could not be created.");
						}
						else {
							//Clears the textboxes
							contentPanel.getCreateView().clearTextBoxFields();

							//Confirms the creation of a new user
							Window.alert("User created!");

							//Navigates back to the LoginView
							contentPanel.changePanel(contentPanel.getLoginView());
						}

					}

				});

			}
			else if(event.getSource() == contentPanel.getCreateView().getCreateCancel()) {
				contentPanel.getCreateView().clearTextBoxFields();
				contentPanel.changePanel(contentPanel.getLoginView());
			}

		}

	}
}
