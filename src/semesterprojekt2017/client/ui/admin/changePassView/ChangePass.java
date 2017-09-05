package semesterprojekt2017.client.ui.admin.changePassView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Code-behind for the ChangePass.xml file. A sub-view added to the "deck of cards" of AdminMainView's DeckLayoutPanel
 *
 */
public class ChangePass extends Composite {

	private static ChangePassUiBinder uiBinder = GWT.create(ChangePassUiBinder.class);

	interface ChangePassUiBinder extends UiBinder<Widget, ChangePass> {
	}
	
	@UiField
	PasswordTextBox oldPasswordTextBox;
	
	@UiField
	PasswordTextBox newPasswordTextBox;
	
	@UiField
	Button changePasswordBtn;

	/**
	 * Constructor for the ChangePassView (admin) 
	 */
	public ChangePass() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//Setting a placeholder text for the old and new password textbox
		oldPasswordTextBox.getElement().setPropertyString("placeholder", "old password");
        newPasswordTextBox.getElement().setPropertyString("placeholder", "new password");
	}
	
	/**
	 * Adds a clickhandler to the change password button, the logic is defined in the AppController AdminController.
	 * @param handler the ClickHandler object added to the button
	 */
	public void addClickHandlers(ClickHandler handler) {
		changePasswordBtn.addClickHandler(handler);
	}
	
	/**
	 * Clears the TextBox fields of the ChangePass view.
	 */
	public void clearTextBoxFields() {
		oldPasswordTextBox.setText("");
		newPasswordTextBox.setText("");
	}

	//Getters
	public PasswordTextBox getOldPasswordTextBox() {
		return oldPasswordTextBox;
	}

	public PasswordTextBox getNewPasswordTextBox() {
		return newPasswordTextBox;
	}

	public Button getChangePasswordBtn() {
		return changePasswordBtn;
	}	
	

}
