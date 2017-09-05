package semesterprojekt2017.client;

import com.google.gwt.core.client.EntryPoint;

import semesterprojekt2017.client.logic.MainController;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Semesterprojekt2017 implements EntryPoint {


	/**
	 * The entry point method. 
	 * 
	 */
	public void onModuleLoad() {

		//Passes the operations to the main AppController
		new MainController();

	}

}
