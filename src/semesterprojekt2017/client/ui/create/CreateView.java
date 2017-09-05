package semesterprojekt2017.client.ui.create;

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
 * Code-behind of the CreateView.xml file. A view for creating a new user.
 *
 */
public class CreateView extends Composite {

	private static CreateUserUiBinder uiBinder = GWT.create(CreateUserUiBinder.class);

	@UiField
	TextBox createFullname;
	@UiField 
	TextBox createUsername;
	@UiField 
	PasswordTextBox createUserPass;
	@UiField
	Button userCreateOK;
	@UiField
	Button userCreateCancel;

	interface CreateUserUiBinder extends UiBinder<Widget, CreateView> {
	}

	/**
	 * Constructor for the CreateView
	 */
	public CreateView() {
		initWidget(uiBinder.createAndBindUi(this));

		//		Setting a placeholder text for the fullname, username and password textbox
		createFullname.getElement().setPropertyString("placeholder", "Please enter your full name");
		createUsername.getElement().setPropertyString("placeholder", "Please enter a username");
		createUserPass.getElement().setPropertyString("placeholder", "Please enter a password");
	}

	/**
	 * Adds clickHandlers to the buttons. The logic is defined in the AppController MainController.
	 * @param clickHandler instance of the ClickHandler interface.
	 */
	public void addClickHandlers(ClickHandler clickHandler) {
		userCreateOK.addClickHandler(clickHandler);
		userCreateCancel.addClickHandler(clickHandler);
	}

	/**
	 * Clears the TextBox fields in the CreateView so the "old" text isn't left there for the next user
	 */
	public void clearTextBoxFields() {
		createFullname.setText("");
		createUsername.setText("");
		createUserPass.setText("");
	}

	//Getters
	public Button getCreateOK() {
		return userCreateOK;
	}

	public Button getCreateCancel() {
		return userCreateCancel;
	}

	public TextBox getCreateFullname() {
		return createFullname;
	}

	public TextBox getCreateUsername() {
		return createUsername;
	}

	public PasswordTextBox getCreateUserPass() {
		return createUserPass;
	}



}
