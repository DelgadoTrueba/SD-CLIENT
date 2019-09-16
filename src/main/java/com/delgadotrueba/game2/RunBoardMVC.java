package com.delgadotrueba.game2;

import com.delgadotrueba.game2.controllers.Player1Controller;
import com.delgadotrueba.game2.controllers.ComputerController;
import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.utils.Turn;
import com.delgadotrueba.game2.views.BoardView;

public class RunBoardMVC {

		public RunBoardMVC() {

			////////////////////////////////////////////////////////////////////////////
			// DATA
			////////////////////////////////////////////////////////////////////////////
			
			// CREATE TURN
			boolean playerOne = false;
			boolean playerTwo = true;
			Turn turn = new Turn(playerOne, playerTwo);
			
			// PARAM GAME
			int NUMBER_OF_ROWS = 4;
			int NUMBER_OF_COLUMNS = 2;
			
			////////////////////////////////////////////////////////////////////////////
			// USER CONTROLLER
			////////////////////////////////////////////////////////////////////////////
			
			// Create Model & View
			BoardModel myModel 	= new BoardModel(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
			BoardView myView 	= new BoardView(1, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS );

			// Tell Model about View. 
			myModel.addObserver(myView);
		
			// Create Controller. tell it about Model and View, initialise model
			Player1Controller player1Controller = new Player1Controller(turn);
			player1Controller.addModel(myModel);
			player1Controller.addView(myView);
			player1Controller.initModel();

			// Tell View about Controller 
			myView.addController(player1Controller);
			
			////////////////////////////////////////////////////////////////////////////
			// COMPUTER CONTROLLER
			////////////////////////////////////////////////////////////////////////////
			ComputerController computerController = new ComputerController(turn, NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
			computerController.addModel(myModel);
			computerController.addView(myView);
			computerController.initModel();
			
			computerController.start();
		}
	
}
