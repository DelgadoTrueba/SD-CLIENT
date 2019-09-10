package com.delgadotrueba.game2;

import com.delgadotrueba.game2.controllers.BoardController;
import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.views.BoardView;

public class RunBoardMVC {
		//The order of instantiating the objects below will be important for some pairs of commands.
		//I haven't explored this in any detail, beyond that the order below works.

		private int start_value = 100;	//initialise model, which in turn initialises view

		public RunBoardMVC() {

			//create Model and View
			BoardModel myModel 	= new BoardModel();
			BoardView myView 	= new BoardView();

			//tell Model about View. 
			myModel.addObserver(myView);
		
			//create Controller. tell it about Model and View, initialise model
			BoardController myController = new BoardController();
			myController.addModel(myModel);
			myController.addView(myView);
			myController.initModel(0, 0, 0);

			//tell View about Controller 
			myView.addController(myController);
			

		}
}
