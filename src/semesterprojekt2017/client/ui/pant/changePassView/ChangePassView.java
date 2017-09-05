package semesterprojekt2017.client.ui.pant.changePassView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Code-behind of the ChangePassView XML file
 *
 */
public class ChangePassView extends Composite {

	private static ChangePassUiBinder uiBinder = GWT.create(ChangePassUiBinder.class);

	interface ChangePassUiBinder extends UiBinder<Widget, ChangePassView> {
	}

	@UiField
	PasswordTextBox oldPasswordTextBox;

	@UiField
	PasswordTextBox newPasswordTextBox;

	@UiField
	Button changePasswordBtn;

	/**
	 * Constructor for the ChangePassView (pant)
	 */
	public ChangePassView() {
		initWidget(uiBinder.createAndBindUi(this));

		//Setting a placeholder text for the old and new password textbox
		oldPasswordTextBox.getElement().setPropertyString("placeholder", "old password");
		newPasswordTextBox.getElement().setPropertyString("placeholder", "new password");
	}

	/**
	 * Adds a ClickHandler to the button
	 * @param handler the object of ClickHandler interface which is added to the button
	 */
	public void addClickHandlers(ClickHandler handler) {
		changePasswordBtn.addClickHandler(handler);
	}

	/**
	 * Clears the TextBox fields in ChangePassView
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
