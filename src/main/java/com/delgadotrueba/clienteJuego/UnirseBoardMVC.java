package com.delgadotrueba.clienteJuego;

import com.delgadotrueba.clienteJuego.juego.mvc.controllers.Player1Controller;
import com.delgadotrueba.clienteJuego.juego.mvc.models.BoardModel;
import com.delgadotrueba.clienteJuego.juego.mvc.models.ObservableBoarModel;
import com.delgadotrueba.clienteJuego.juego.mvc.views.BoardView;
import com.delgadotrueba.game2.interfazRMI.IONetworkClient;
import com.delgadotrueba.game2.interfazRMI.InterfazClienteRMI;
import com.delgadotrueba.game2.interfazRMI.exceptions.RMIClientException;

public class UnirseBoardMVC {

		public UnirseBoardMVC(byte OID) throws RMIClientException{

			////////////////////////////////////////////////////////////////////////////
			// DATA
			////////////////////////////////////////////////////////////////////////////
			
			// PARAM GAME
			int NUMBER_OF_ROWS = 4;
			int NUMBER_OF_COLUMNS = 4;
			
			//RMI
			InterfazClienteRMI clienteRMI = new InterfazClienteRMI();
			clienteRMI.setOID(OID);
			//FIN COSAS ESPECIALES
			
			clienteRMI.obtenerTiposCartas();
			
			BoardModel myModel = new BoardModel(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
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
