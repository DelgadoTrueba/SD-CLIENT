package com.delgadotrueba.game2;

import com.delgadotrueba.game2.controllers.Player1Controller;
import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.notifications.ObservableBoarModel;
import com.delgadotrueba.game2.views.BoardView;

import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.InterfazClienteRMI;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.utils.IONetworkClient;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.utils.IONetworkUtils;

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
			int[][] tiposInt = null;
			try {
				clienteRMI.newGame();
				byte[] tipos = clienteRMI.obtenerTiposCartas();
				tiposInt = IONetworkUtils.tradudirTipoCarta_bytes(tipos);
			}catch (Exception e) {
				e.printStackTrace();
			}
			BoardModel myModel = new BoardModel(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS, tiposInt);
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
