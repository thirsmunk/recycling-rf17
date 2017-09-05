package semesterprojekt2017.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import semesterprojekt2017.client.ui.admin.adminMainView.AdminMainView;
import semesterprojekt2017.client.ui.create.CreateView;
import semesterprojekt2017.client.ui.login.LoginView;
import semesterprojekt2017.client.ui.pant.pantMainView.PantMainView;

/** This is the panel which is added to the root panel
 * The main GWT panel in this class is a DeckLayoutPanel; ContentPanel. The DeckLayoutPanel doesn't
 * contain any widgets but contains all the following main panels:
 * 1) LoginView, which is the first page shown to the user when the program is executed
 * 2) CreateUser, which is shown when the user wants to create a new pant user
 * 2) PantView, which is shown when the login information corresponds with a user in the database
 * 3) AdminView, which is shown when the login information corresponds with the admin information
 * 
 * Furthermore, this class works as the main PageController
 */

//The class must extend the Composite class
public class ContentPanel extends Composite {

	//The main panel in the class
	DeckLayoutPanel contentPanel;

	//The four main panels in the application 
	LoginView loginView;
	CreateView createView;
	PantMainView pantView;
	AdminMainView adminView;

	/**
	 * Constructor for ContentPanel. Initializes the "main" sub-views and adds them to the DeckLayoutPanel, which is hooked upon
	 * the RootLayoutPanel in the MainController
	 */
	public ContentPanel () {

		//Initializes the contentPanel so the other panels can be added to the "deck of cards" 
		contentPanel = new DeckLayoutPanel();

		loginView = new LoginView();
		contentPanel.add(loginView);

		createView = new CreateView();
		contentPanel.add(createView);

		pantView = new PantMainView();
		contentPanel.add(pantView);

		adminView = new AdminMainView();
		contentPanel.add(adminView);

		//Selects the loginView to be shown first
		contentPanel.showWidget(loginView);

		//Initializes the main widget in this class
		initWidget(contentPanel);
	}

	/**
	 * Changes the panels in the main DeckLayoutPanel 
	 * @param panel the panel to be changed to
	 */
	public void changePanel(Widget panel) {
		contentPanel.showWidget(panel);
	}

	//Getters required for retrieving the panels 
	public LoginView getLoginView() {
		return loginView;
	}

	public CreateView getCreateView() {
		return createView;
	}


	public PantMainView getPantView() {
		return pantView;
	}

	public AdminMainView getAdminView() {
		return adminView;
	}


}
