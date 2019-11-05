package com.delgadotrueba.clienteJuego;

import com.delgadotrueba.clienteJuego.juego.controllers.Player1Controller;
import com.delgadotrueba.clienteJuego.juego.models.BoardModel;
import com.delgadotrueba.clienteJuego.juego.notifications.ObservableBoarModel;
import com.delgadotrueba.clienteJuego.juego.views.BoardView;
import com.delgadotrueba.game2.interfazRMI.IONetworkClient;
import com.delgadotrueba.game2.interfazRMI.InterfazClienteRMI;

public class RunBoardMVC {

		public RunBoardMVC(){

			////////////////////////////////////////////////////////////////////////////
			// DATA
			////////////////////////////////////////////////////////////////////////////
			
			// PARAM GAME
			int NUMBER_OF_ROWS = 4;
			int NUMBER_OF_COLUMNS = 4;
			
			//RMI
			InterfazClienteRMI clienteRMI = new InterfazClienteRMI();
			byte[][] tipos = null;
			try {
				clienteRMI.newGame();
				tipos = clienteRMI.obtenerTiposCartas();
			}catch (Exception e) {
				e.printStackTrace();
			}
			BoardModel myModel = new BoardModel(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS, tipos);
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
			Player1Controller player1Controller = new Player1Controller(clienteRMI);
			player1Controller.addModel(observable_myModel);
			player1Controller.addView(myView);
			player1Controller.initModel();

			// Tell View about Controller 
			myView.addController(player1Controller);
		}
	
}
