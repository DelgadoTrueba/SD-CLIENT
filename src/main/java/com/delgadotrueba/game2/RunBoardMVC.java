package com.delgadotrueba.game2;

import com.delgadotrueba.game2.controllers.Player1Controller;
import com.delgadotrueba.game2.controllers.Player2Controller;
import com.delgadotrueba.game2.RMI.BoardAlreadyCreatedException;
import com.delgadotrueba.game2.RMI.BoardNotInitializedException;
import com.delgadotrueba.game2.RMI.BoardRMI;
import com.delgadotrueba.game2.controllers.Computer2Controller;
import com.delgadotrueba.game2.controllers.ComputerController;
import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.notifications.ObservableBoarModel;
import com.delgadotrueba.game2.utils.Turn;
import com.delgadotrueba.game2.views.BoardView;

public class RunBoardMVC {

		public RunBoardMVC() throws BoardAlreadyCreatedException, BoardNotInitializedException {

			////////////////////////////////////////////////////////////////////////////
			// DATA
			////////////////////////////////////////////////////////////////////////////
			
			// PARAM GAME
			int NUMBER_OF_ROWS = 4;
			int NUMBER_OF_COLUMNS = 2;
			
			//RMI
			BoardRMI rmi = new BoardRMI(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
			rmi.initializeNewBoard();
			BoardModel myModel = rmi.getBoard();
			////////////////////////////////////////////////////////////////////////////
			// USER CONTROLLER
			////////////////////////////////////////////////////////////////////////////
			
			// Create Model & View
			//mymodel
			ObservableBoarModel observable_myModel = new ObservableBoarModel(myModel);
			BoardView myView 	= new BoardView(1, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS );

			// Tell Model about View. 
			observable_myModel.addObserver(myView);
		
			
			
			// Create Controller. tell it about Model and View, initialise model
			Player1Controller player1Controller = new Player1Controller(rmi);
			player1Controller.addModel(observable_myModel);
			player1Controller.addView(myView);
			player1Controller.initModel();

			// Tell View about Controller 
			myView.addController(player1Controller);
			
			////////////////////////////////////////////////////////////////////////////
			// COMPUTER CONTROLLER
			////////////////////////////////////////////////////////////////////////////
			ComputerController computerController = new ComputerController(rmi, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
			computerController.addModel(observable_myModel);
			computerController.addView(myView);
			computerController.initModel();
			
			computerController.start();
			
			
			
			/**************LA OTRA VISTA*******************/
			////////////////////////////////////////////////////////////////////////////
			// USER CONTROLLER
			////////////////////////////////////////////////////////////////////////////
			
			// Create Model & View
			//mymodel
			BoardModel myModel2 = rmi.getBoard();
			ObservableBoarModel observable_myModel2 = new ObservableBoarModel(myModel2);
			BoardView myView2 	= new BoardView(1, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS );
			
			// Tell Model about View. 
			observable_myModel2.addObserver(myView2);
			
			
			
			// Create Controller. tell it about Model and View, initialise model
			Player2Controller player2Controller = new Player2Controller(rmi);
			player2Controller.addModel(observable_myModel2);
			player2Controller.addView(myView2);
			player2Controller.initModel();
			
			// Tell View about Controller 
			myView2.addController(player2Controller);
			
			////////////////////////////////////////////////////////////////////////////
			// COMPUTER CONTROLLER
			////////////////////////////////////////////////////////////////////////////
			Computer2Controller computer2Controller = new Computer2Controller(rmi, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
			computer2Controller.addModel(observable_myModel2);
			computer2Controller.addView(myView2);
			computer2Controller.initModel();
			
			computer2Controller.start();
		}
	
}
