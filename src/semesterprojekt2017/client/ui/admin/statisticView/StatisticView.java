package semesterprojekt2017.client.ui.admin.statisticView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.NumberLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The code-behind class of StatisticsView.XML
 *
 */
public class StatisticView extends Composite {

	private static StatistikUiBinder uiBinder = GWT.create(StatistikUiBinder.class);

	@UiField 
	NumberLabel<Integer> numberOfUsersLbl;

	@UiField
	NumberLabel<Double> totalCollectedLbl;

	@UiField
	NumberLabel<Double> averageCollectedLbl;

	interface StatistikUiBinder extends UiBinder<Widget, StatisticView> {
	}

	/**
	 * Constructor for the StatisticsView
	 */
	public StatisticView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Clears the statistic values
	 */
	public void clearStatistics() {
		numberOfUsersLbl.setValue(null);
		totalCollectedLbl.setValue(null);
		averageCollectedLbl.setValue(null);
	}

	//Getters
	public NumberLabel<Integer> getNumberOfUsersLbl() {
		return numberOfUsersLbl;
	}

	public NumberLabel<Double> getTotalCollectedLbl() {
		return totalCollectedLbl;
	}

	public NumberLabel<Double> getAverageCollectedLbl() {
		return averageCollectedLbl;
	}


}
