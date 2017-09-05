package semesterprojekt2017.client.ui.admin.pantViewAdmin.singlePantViewAdmin;

import java.util.Comparator;
import java.util.Date;

import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.cell.client.DateCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import semesterprojekt2017.shared.Pant;

/**
 * The code-behind of the SinglePantViewAdmin XML file. Furthermore this class contains methods for 
 * initializing a table and adding columns. 
 *
 */
public class SinglePantViewAdmin extends Composite {

	private static SinglePantViewAdminUiBinder uiBinder = GWT.create(SinglePantViewAdminUiBinder.class);

	@UiField
	CellTable<Pant> singlePantViewTable;

	@UiField
	SimplePager simplePager;

	@UiField
	Button singlePantBackBtn;

	@UiField
	NumberLabel<Double> totalPantCollectedLbl;

	private ActionCell.Delegate<Pant> actionCellDelete;

	interface SinglePantViewAdminUiBinder extends UiBinder<Widget, SinglePantViewAdmin> {
	}

	/**
	 * Constructor for the SinglePantViewAdmin
	 */
	public SinglePantViewAdmin() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Adds a ClickHandler to the "Back" button, the logic is defined in the AppController AdminController.
	 * @param handler the ClickHandler object
	 */
	public void addClickHandlers(ClickHandler handler) {
		singlePantBackBtn.addClickHandler(handler);
	}

	/**
	 * "Visualizes" the SinglePantViewTable and adds a ColumnSortHandler to make sorting possible 
	 * Furthermore it also adds a SimplePager to the CellTable. 
	 * @param dataProvider the method receives this ListDataProvider of type Pant object, which is all the data
	 * to be shown
	 */
	public void initSinglePantViewTable(ListDataProvider<Pant> dataProvider) {

		//Attaches a column sort handler to the ListDataProvider to sort the columns
		ListHandler<Pant> sortHandler = new ListHandler<Pant>(dataProvider.getList());
		singlePantViewTable.addColumnSortHandler(sortHandler);

		//Initializes the columns
		initSinglePantViewColumns(sortHandler);

		//Sets the amount of rows shown
		singlePantViewTable.setPageSize(10);

		simplePager.setDisplay(singlePantViewTable);

		//Making the magic happen, adding a "display" to the table, dataProvider 
		//contains a list of all the pant for the chosen user

		dataProvider.addDataDisplay(singlePantViewTable);
	}

	/**
	 * Adds columns to the table and makes sorting possible.
	 * @param sortHandler receives a ListHandler of type Pant object, which is used to perform comparison between values
	 */
	private void initSinglePantViewColumns(ListHandler<Pant> sortHandler) {
		//Pant Type Column
		Column<Pant, String> panttypeColumn = new Column<Pant, String>(new TextCell()) {
			@Override
			public String getValue(Pant pant) {
				return pant.getPanttype();
			}
		};

		//Makes the pant-type sortable
		panttypeColumn.setSortable(true);
		sortHandler.setComparator(panttypeColumn, new Comparator<Pant>() {

			@Override
			public int compare(Pant p1, Pant p2) {
				return p1.getPanttype().compareTo(p2.getPanttype());
			}

		});

		//Adds the column to the table
		singlePantViewTable.addColumn(panttypeColumn, "Pant Type");

		//Sets the size of the column using Unit.PX
		singlePantViewTable.setColumnWidth(panttypeColumn, 10, Unit.PX);
		//Pant Value Column
		Column<Pant, Number> pantValue = new Column<Pant, Number>(new NumberCell()) {
			@Override
			public Number getValue(Pant pant) {
				return pant.getValue();
			}
		};

		//Adds column to table
		singlePantViewTable.addColumn(pantValue, "Pant Value");

		//Size
		singlePantViewTable.setColumnWidth(pantValue, 10, Unit.PX);

		//Date column
		Column<Pant, Date> pantDate = new Column<Pant, Date>(new DateCell()) {
			@Override
			public Date getValue(Pant pant) {
				return pant.getDate();
			}
		};

		//Makes the date column sortable
		pantDate.setSortable(true);
		sortHandler.setComparator(pantDate, new Comparator<Pant>() {

			@Override
			public int compare(Pant d1, Pant d2) {
				return d1.getDate().compareTo(d2.getDate());
			}
		});

		//Adds column to table
		singlePantViewTable.addColumn(pantDate, "Date");

		//Size
		singlePantViewTable.setColumnWidth(pantDate, 20, Unit.PX);

		ActionCell<Pant> deletePantCell = new ActionCell<>("Delete Entry", actionCellDelete);
		Column<Pant, Pant> deleteColumn = new Column<Pant, Pant>(deletePantCell) {

			@Override
			public Pant getValue(Pant pant) {
				return pant;
			}

		};
		//Styling the actioncell button
		deleteColumn.setCellStyleNames("actioncell");

		singlePantViewTable.addColumn(deleteColumn, "Delete Entry");
		singlePantViewTable.setColumnWidth(deleteColumn, 7, Unit.PX);

	}

	/**
	 * Adding a "handler" to the ActionCell in this table. The logic is defined in the AppController. 
	 * @param actionCellDelete the parameter's value is transferred to the actionCellDelete ActionCell
	 */
	public void addDeletePantClickHandler(ActionCell.Delegate<Pant> actionCellDelete) { 
		this.actionCellDelete = actionCellDelete;
	}

	public Button getBackBtn() {
		return singlePantBackBtn;
	}

	public NumberLabel<Double> getPantCollectedLbl() {
		return totalPantCollectedLbl;
	}

}

