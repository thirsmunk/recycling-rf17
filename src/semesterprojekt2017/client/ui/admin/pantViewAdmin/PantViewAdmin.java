package semesterprojekt2017.client.ui.admin.pantViewAdmin;

import java.util.Comparator;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import semesterprojekt2017.shared.UserDTO;

/**
 * Code-behind of the PantViewAdmin XMl file. Contains methods for "visualizing" and initializing a table. 
 *
 */
public class PantViewAdmin extends Composite {

	private static PantViewAdminUiBinder uiBinder = GWT.create(PantViewAdminUiBinder.class);

	@UiField
	CellTable<UserDTO> pantViewAdminTable;

	@UiField 
	SimplePager simplePager;

	private ActionCell.Delegate<UserDTO> actionCellView;
	private ActionCell.Delegate<UserDTO> actionCellDelete;

	interface PantViewAdminUiBinder extends UiBinder<Widget, PantViewAdmin> {
	}

	/**
	 * Constructor for the PantViewAdmin
	 */
	public PantViewAdmin() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * "Visualizes" the pantViewAdminTable and adds a ColumnSortHandler to make sorting possible 
	 * Furthermore it also adds a SimplePager to the CellTable. 
	 * @param dataProvider the method receives this ListDataProvider of type UserDTO object, which is all the data
	 * to be shown
	 */
	public void initPantInformationTable(ListDataProvider<UserDTO> dataProvider) {

		//Attaches a column sort handler to the ListDataProvider to sort the columns
		ListHandler<UserDTO> sortHandler = new ListHandler<UserDTO>(dataProvider.getList());
		pantViewAdminTable.addColumnSortHandler(sortHandler);

		//Sets amount of rows shown 
		pantViewAdminTable.setPageSize(15);

		//Adds the pager to the table
		simplePager.setDisplay(pantViewAdminTable);

		//Initializes the columns
		initPantInformationColumns(sortHandler);

		/*Making the magic happen, adding a "display" to the table, dataProvider 
		 * contains a list of all the users in the system
		 */
		dataProvider.addDataDisplay(pantViewAdminTable);

	}

	/**
	 * Adds columns to the table and makes sorting possible.
	 * @param sortHandler receives a ListHandler of type Pant object, which is used to perform comparison between values
	 */
	private void initPantInformationColumns(ListHandler<UserDTO> sortHandler) {

		//ID column
		Column<UserDTO, Number> userID = new Column<UserDTO, Number>(new NumberCell()) {

			@Override
			public Number getValue(UserDTO id) {
				return id.getId();
			}

		};

		//Makes the ID column sortable
		userID.setSortable(true);
		sortHandler.setComparator(userID, new Comparator<UserDTO>() {

			@Override
			public int compare(UserDTO id1, UserDTO id2) {
				return Integer.compare(id1.getId(), id2.getId());
			}

		});

		//Adds column to table
		pantViewAdminTable.addColumn(userID, "Pant ID");

		pantViewAdminTable.setColumnWidth(userID, 10, Unit.PX);

		//Full name column
		Column<UserDTO, String> userFullName = new Column<UserDTO, String>(new TextCell()) {

			@Override
			public String getValue(UserDTO pDTO) {
				return pDTO.getFullname();
			}

		};

		userFullName.setSortable(true);
		sortHandler.setComparator(userFullName, new Comparator<UserDTO>() {

			@Override
			public int compare(UserDTO pDTO1, UserDTO pDTO2) {

				return pDTO1.getFullname().compareTo(pDTO2.getFullname());
			}

		});

		pantViewAdminTable.addColumn(userFullName, "Full Name");

		pantViewAdminTable.setColumnWidth(userFullName, 40, Unit.PX);

		//Username column
		Column<UserDTO, String> userName = new Column<UserDTO, String>(new TextCell()) {

			@Override
			public String getValue(UserDTO user) {
				return user.getUsername();
			}

		};

		userName.setSortable(true);
		sortHandler.setComparator(userName, new Comparator<UserDTO>() {

			@Override
			public int compare(UserDTO u1, UserDTO u2) {
				return u1.getUsername().compareTo(u2.getUsername());
			}

		});

		pantViewAdminTable.addColumn(userName, "Username");
		pantViewAdminTable.setColumnWidth(userName, 10, Unit.PX);

		//Password column
		Column<UserDTO, String> userPassword = new Column<UserDTO, String>(new TextCell()) {

			@Override
			public String getValue(UserDTO user) {
				return user.getPassword();
			}

		};

		pantViewAdminTable.addColumn(userPassword, "Password");
		pantViewAdminTable.setColumnWidth(userPassword, 7, Unit.PX);

		//View pant action-cell
		ActionCell<UserDTO> viewPantCell = new ActionCell<>("View Pant", actionCellView);
		Column<UserDTO, UserDTO> viewPantColumn = new Column<UserDTO, UserDTO>(viewPantCell) {

			@Override
			public UserDTO getValue(UserDTO user) {
				return user;
			}

		};
		//Styling the actioncell button
		viewPantColumn.setCellStyleNames("actioncell");

		pantViewAdminTable.addColumn(viewPantColumn, "View Pant");
		pantViewAdminTable.setColumnWidth(viewPantColumn, 20, Unit.PX);

		//Delete user action-cell
		ActionCell<UserDTO> deleteUserCell = new ActionCell<>("Delete User", actionCellDelete);
		Column<UserDTO, UserDTO> deleteColumn = new Column<UserDTO, UserDTO>(deleteUserCell) {


			@Override
			public UserDTO getValue(UserDTO user) {
				return user;
			}

		};
		//Styling the actioncell button
		deleteColumn.setCellStyleNames("actioncell");

		pantViewAdminTable.addColumn(deleteColumn, "Delete User");
		pantViewAdminTable.setColumnWidth(deleteColumn, 20, Unit.PX);

	}

	/**
	 * Method for adding a handler to the "Delete User" button, the logic is found in the AppController AdminController
	 * @param actionCell the value of actionCell is transferred to the actionCellView 
	 */
	public void addClickHandlerView(ActionCell.Delegate<UserDTO> actionCell) {
		this.actionCellView = actionCell;
	}

	/**
	 * Method for adding a handler to the "Delete User" button, the logic is found in the AppController AdminController
	 * @param actionCell the value of actionCell is transferred to the actionCellDelete
	 */
	public void addClickHandlerDeleteUser(ActionCell.Delegate<UserDTO> actionCell) {
		this.actionCellDelete = actionCell;
	}

}
