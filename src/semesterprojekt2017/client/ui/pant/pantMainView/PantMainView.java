package semesterprojekt2017.client.ui.pant.pantMainView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import semesterprojekt2017.client.ui.pant.changePassView.ChangePassView;
import semesterprojekt2017.client.ui.pant.pantView.PantUserView;

/**
 * The code-behind of the PantMainView XML file. Also has the role of a sub-PageController since it has a DeckLayoutPanel 
 * in the center of the DockLayoutPanel.
 *
 */
public class PantMainView extends Composite {

	private static PantViewUiBinder uiBinder = GWT.create(PantViewUiBinder.class);

	// Oprette objekter af views til centerpanel
	private PantUserView pantUserView;
	private ChangePassView changePassView;

	// Making UiFields referencing the ui:fields in the ui.xml file
	@UiField
	DeckLayoutPanel centerPantPanel;
	@UiField
	Button pantProfileBtn;
	@UiField
	Button pantChangePasswordBtn;
	@UiField
	Button pantLogoutBtn;	

	interface PantViewUiBinder extends UiBinder<Widget, PantMainView> {
	}

	/**
	 * Constructor for the PantMainView. Initializes the sub-views and adds 
	 * them to the DeckLayoutPanel situated in the DockLayoutPanel
	 */
	public PantMainView() {
		initWidget(uiBinder.createAndBindUi(this));

		pantUserView = new PantUserView();
		centerPantPanel.add(pantUserView);

		changePassView = new ChangePassView();
		centerPantPanel.add(changePassView);

		// Initially shows the pantUserView
		centerPantPanel.showWidget(pantUserView);
	}

	/**
	 * Adds ClickHandlers to the menu buttons, logic is defined in the AppController PantController
	 * @param handler the object of ClickHandler interface which is added to the buttons
	 */
	public void addClickHandlers(ClickHandler handler) {
		pantProfileBtn.addClickHandler(handler);
		pantChangePasswordBtn.addClickHandler(handler);
		pantLogoutBtn.addClickHandler(handler);
	}


	/**
	 * Changes the view in the PantMainView DeckLayoutPanel
	 * @param panel the panel to be changed to
	 */
	public void changeView(Widget panel) {
		centerPantPanel.showWidget(panel);
	}

	//Getters

	public ChangePassView getChangePassView() {
		return changePassView;
	}

	public PantUserView getPantUserView() {
		return pantUserView;
	}

	public Button getPantProfileBtn() {
		return pantProfileBtn;
	}

	public Button getPantChangePasswordBtn() {
		return pantChangePasswordBtn;
	}

	public Button getPantLogoutBtn() {
		return pantLogoutBtn;
	}

}