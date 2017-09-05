package semesterprojekt2017.client.ui.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Code-behind of the LoginView XML file. 
 *
 */
public class LoginView extends Composite {

	private static LoginViewUiBinder uiBinder = GWT.create(LoginViewUiBinder.class);
	
//	Making UiFields referencing the ui:fields in the ui.xml file
	@UiField 
	TextBox usernameTextBox;
	@UiField
	PasswordTextBox passwordTextBox;
	@UiField
	Button loginBtn;
	@UiField
	Button createUserBtn;

	interface LoginViewUiBinder extends UiBinder<Widget, LoginView> {
	}

	/**
	 * Constructor for the LoginView
	 */
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
		
//		Setting a placeholder text for the username and password textbox
		usernameTextBox.getElement().setPropertyString("placeholder", "username");
        passwordTextBox.getElement().setPropertyString("placeholder", "password");
	}

	/**
	 * Adds ClickHandlers to the buttons, logic is defined in the MainController
	 * @param handler the object of ClickHandler interface which is added to the buttons
	 */
	public void addClickHandlers(ClickHandler handler) {
		loginBtn.addClickHandler(handler);
		createUserBtn.addClickHandler(handler);
	}
	
	/**
	 * Clears the TextBox fields in the LoginView
	 */
	public void clearTextBoxFields() {
		usernameTextBox.setText("");
		passwordTextBox.setText("");
	}
	
	//Getters for retrieving the information provided in the textboxes and for retrieving the click origin
	public TextBox getUsernameTextBox() {
		return usernameTextBox;
	}
	
	public PasswordTextBox getPasswordTextBox() {
		return passwordTextBox;
	}
	
	public Button getLoginBtn() {
		return loginBtn;
	}
	
	public Button getCreateBtn() {
		return createUserBtn;
	}
}
