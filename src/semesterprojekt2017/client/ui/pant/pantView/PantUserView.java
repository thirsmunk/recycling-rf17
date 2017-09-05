package semesterprojekt2017.client.ui.pant.pantView;

import java.util.Comparator;
import java.util.Date;

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
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import semesterprojekt2017.shared.Pant;

/**
 * Code-behind of the PantUserView XML file. Also contains methods for initializing the table and adding columns.
 *
 */
public class PantUserView extends Composite {

	private static PantOverblikViewUiBinder uiBinder = GWT.create(PantOverblikViewUiBinder.class);

	@UiField
	NumberLabel<Double> totalPantCollectedLbl;
	@UiField 
	CellTable<Pant> pantViewTable;

	@UiField
	RadioButton aRadioBtn;

	@UiField
	RadioButton bRadioBtn;

	@UiField
	RadioButton cRadioBtn;

	@UiField
	IntegerBox pantValueBox;

	@UiField
	Button addPantBtn;

	@UiField
	SimplePager simplePager;

	interface PantOverblikViewUiBinder extends UiBinder<Widget, PantUserView> {
	}


	/**
	 * Constructor for the PantUserView
	 */
	public PantUserView() {
		initWidget(uiBinder.createAndBindUi(this));

		//		Setting a placeholder text for the pantValue textbox
		pantValueBox.getElement().setPropertyString("placeholder", "e.g. 5");

		//Setting the amount of rows shown per "page"
		pantViewTable.setPageSize(5);

		//Adding the pager to the CellTable
		simplePager.setDisplay(pantViewTable);

	}

	/**
	 * Adds a ClickHandler to the button for adding pant
	 * @param handler the object of ClickHandler interface which is added to the button
	 */
	public void addClickHandlers(ClickHandler handler) {
		addPantBtn.addClickHandler(handler);
	}

	/**
	 * Clears the text in the TextBox field in the PantUserView and clears the Radio Buttons
	 */
	public void clearTextBoxFields() {
		pantValueBox.setText("");
		aRadioBtn.setValue(false);
		bRadioBtn.setValue(false);
		cRadioBtn.setValue(false);
	}

	/**
	 * Initializes/visualizes the table and adds a ColumnSortHandler and Columns (by calling the method initPantTableColumns)
	 * @param dataProvider the ListDataProvider of type Pant containing all the data
	 */
	public void initPantTable(ListDataProvider<Pant> dataProvider) {
		//Attaches a column sort handler to the ListDataProvider to sort the columns
		ListHandler<Pant> sortHandler = new ListHandler<Pant>(dataProvider.getList());
		pantViewTable.addColumnSortHandler(sortHandler);

		//Initializes the columns
		initPantTableColumns(sortHandler);

		/*Making the magic happen, adding a "display" to the table, dataProvider 
		 * contains a list of all the collected pant for the user
		 */
		dataProvider.addDataDisplay(pantViewTable);

	}


	//Add ListHandler<Pant> sortHandler as parameter to add sorters, check dokbook userview
	/**
	 * Adds the columns to the table and adds the sorting logic
	 * @param sortHandler is the ListHandler of type Pant which makes sorting possible
	 */
	private void initPantTableColumns(ListHandler<Pant> sortHandler) {
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
		pantViewTable.addColumn(panttypeColumn, "Pant Type");

		//Sets the size of the column using Unit.PX
		pantViewTable.setColumnWidth(panttypeColumn, 10, Unit.PX);
		//Pant Value Column
		Column<Pant, Number> pantValue = new Column<Pant, Number>(new NumberCell()) {
			@Override
			public Number getValue(Pant pant) {
				return pant.getValue();
			}
		};

		//Adds column to table
		pantViewTable.addColumn(pantValue, "Pant Value");

		//Size
		pantViewTable.setColumnWidth(pantValue, 10, Unit.PX);

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
		pantViewTable.addColumn(pantDate, "Date");

		//Size
		pantViewTable.setColumnWidth(pantDate, 20, Unit.PX);

	}

	//Getters

	public NumberLabel<Double> getTotalPantCollectedLbl() {
		return totalPantCollectedLbl;
	}

	public RadioButton getARadioBtn() {
		return aRadioBtn;
	}

	public RadioButton getBRadioBtn() {
		return bRadioBtn;
	}

	public RadioButton getCRadioBtn() {
		return cRadioBtn;
	}

	public IntegerBox getPantValueBox() {
		return pantValueBox;
	}

	public CellTable<Pant> getPantTable() {
		return pantViewTable;
	}


}
