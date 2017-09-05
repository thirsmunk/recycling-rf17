package semesterprojekt2017.client.ui.admin.adminMainView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import semesterprojekt2017.client.ui.admin.changePassView.ChangePass;
import semesterprojekt2017.client.ui.admin.pantViewAdmin.PantViewAdmin;
import semesterprojekt2017.client.ui.admin.pantViewAdmin.singlePantViewAdmin.SinglePantViewAdmin;
import semesterprojekt2017.client.ui.admin.statisticView.StatisticView;

/**
 * The code-behind file of the MainView XML also having the function of a sub-PageController because of the 
 * DeckLayoutPanel in the center.
 *
 */
public class AdminMainView extends Composite {

	private static AdminViewUiBinder uiBinder = GWT.create(AdminViewUiBinder.class);


	private ChangePass changePass;
	private StatisticView statisticView;
	private PantViewAdmin pantView;
	private SinglePantViewAdmin singlePantView;

	@UiField
	DeckLayoutPanel centerAdminPanel;
	@UiField
	Button infoBtn;
	@UiField
	Button statsBtn;
	@UiField
	Button changePasswordBtn;
	@UiField
	Button logoutBtn;


	interface AdminViewUiBinder extends UiBinder<Widget, AdminMainView> {
	}

	/**
	 * Constructor for the AdminMainView
	 * Initializes the sub-views and adds them to the DeckLayoutPanel situated in the center of the DockLayoutPanel
	 */
	public AdminMainView() {
		initWidget(uiBinder.createAndBindUi(this));


		changePass = new ChangePass();
		centerAdminPanel.add(changePass);

		statisticView = new StatisticView();
		centerAdminPanel.add(statisticView);

		pantView = new PantViewAdmin();
		centerAdminPanel.add(pantView);

		singlePantView = new SinglePantViewAdmin();
		centerAdminPanel.add(singlePantView);

		centerAdminPanel.showWidget(pantView);

	}

	/**
	 * Adds ClickHandlers to the buttons in the menu. The logic is defined in the AppController AdminController.
	 * @param handler the object of the ClickHandler interface which is added to the buttons
	 */
	public void addClickHandlers(ClickHandler handler) {
		infoBtn.addClickHandler(handler);
		statsBtn.addClickHandler(handler);
		changePasswordBtn.addClickHandler(handler);
		logoutBtn.addClickHandler(handler);
	}

	/**
	 * Responsible for changing the views in the DeckLayoutPanel found in the center of AdminMainView's DockLayoutPanel.
	 * @param panel the view to be changed to
	 */
	public void changeView(Widget panel){
		centerAdminPanel.showWidget(panel);
	}

	// Getters for views


	public ChangePass getChangePassView() {
		return changePass;
	}

	public StatisticView getStatistikView() {
		return statisticView;
	}

	public PantViewAdmin getPantInfoView() {
		return pantView;
	}

	public SinglePantViewAdmin getSinglePantView() {

		return singlePantView;
	}

	// Getters for buttons
	public Button getInfoBtn() {
		return infoBtn;		
	}

	public Button getStatsBtn() {
		return statsBtn;
	}

	public Button getChangePasswordBtn() {
		return changePasswordBtn;
	}

	public Button getLogoutBtn() {
		return logoutBtn;
	}
}
