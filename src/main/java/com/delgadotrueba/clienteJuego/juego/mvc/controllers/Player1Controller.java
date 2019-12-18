package com.delgadotrueba.clienteJuego.juego.mvc.controllers;

import java.util.ArrayList;
import java.util.Random;

import com.delgadotrueba.clienteJuego.juego.mvc.models.BoardModel;
import com.delgadotrueba.clienteJuego.juego.mvc.models.ObservableBoarModel;
import com.delgadotrueba.clienteJuego.juego.mvc.utils.ErrorHandler;
import com.delgadotrueba.clienteJuego.juego.mvc.utils.Turn;
import com.delgadotrueba.clienteJuego.juego.mvc.views.BoardView;
import com.delgadotrueba.clienteJuego.juego.mvc.views.CellView;
import com.delgadotrueba.game2.interfazRMI.IGame;
import com.delgadotrueba.game2.interfazRMI.InterfazClienteRMI;
import com.delgadotrueba.game2.interfazRMI.exceptions.RMIClientException;

public class Player1Controller implements java.awt.event.ActionListener {
	
	private ObservableBoarModel model;
	private BoardView view;
	
	private int numOfMatched_P1 = 0;
	private int numOfMatched_P2 = 0;

	
	private InterfazClienteRMI rmi;
	
	private Turn turn;
	
	public Player1Controller(InterfazClienteRMI rmi) {	
		this.turn = new Turn(true, false);
		this.rmi = rmi;
		// Must be empty
	} 
	
	public void addModel(ObservableBoarModel m){
		this.model = m;
	}

	public void addView(BoardView v){
		this.view = v;
	}
	
	public void initModel() throws RMIClientException{		
		this.init();
	}

	//invoked when a button is pressed	
	public void actionPerformed(java.awt.event.ActionEvent event){

		if (event == null) {
		   ErrorHandler.error("BoardController: ", "actionPermormed(ActionEvent) received null", false);
		   return;
		}
		
		// Flush out cases where we don't care
		if (!(event.getSource() instanceof CellView)) {
			return;
		}
		
  		// START GAME
		if (!turn.isPlayerOne()) {
			this.view.notIsYourTurn();
		}
		
		CellView cellView = (CellView) event.getSource();
		int row = cellView.getRow();
		int col = cellView.getColumn();
		
  		if (!this.model.isCardValid(row, col)) {
  			return;
  		}
  		
  		this.model.setCardSelectedJ1(row, col);

  		if ( this.model.isPlayable() ) {

  			int[][] resul = this.model.getSelectedCards();
  			
  			byte r1 = (byte) resul[0][0];
  			byte c1 = (byte) resul[0][1];

  			byte r2 = (byte) resul[1][0];
  			byte c2 = (byte) resul[1][1];
  			
  				try {
  					if (this.rmi.matchCardsP1(r1, c1, r2, c2)){
  	  	  				
  	  	  				this.model.setSelectedCardsMatched();
  	  	  				
  	  	  				this.numOfMatched_P1 = rmi.obtenerPuntosJ1();
  	  	  				this.view.displayNumOfMatchedPairs_P1(this.numOfMatched_P1);	
  	  	  		  		
  	  	  				if(this.rmi.isSolved()) {
	  	  	  				if(this.numOfMatched_P1 > this.numOfMatched_P2 ) {
	  	  						this.view.playerWin();
	  	  					}else if(this.numOfMatched_P1 == this.numOfMatched_P2){
	  	  						this.view.playerTie();
	  	  					}else{
	  	  						this.view.playerLose();
	  	  					}
  	  	  				}
  	  	  				
  	  	  			} else {
  	  	  				turn.changeTurn();	
  	  	  				this.model.setSelectedCardsHiddenJ1(); 				
  	  	   				this.view.showInfoTurnPlayer2();
  	  	   				/***********************************************/
  	  	   				int emparejadasAnt = model.getMatchedCard();
  	  	   				int emparejada = rmi.obtenerCartasEmparejadas();
  	  	   				int seleccionadasJ2 = rmi.obtenerCartasSeleccionadasJ2();
  	  	   				
  	  	   				int jugadaParejas = 0;
  	  	   				int jugadaResto;
  	  	   				  	  	   				
  	  	   				if(emparejadasAnt == emparejada) {
		  	   				jugadaResto = seleccionadasJ2;
		  	   				this.selectCards(jugadaResto);
  	  	   				}
  	  	   				else{
		  	   				if(emparejadasAnt == 0) {
		  	   					jugadaParejas = emparejada;
		  	   				}
		  	   				else {
		  	   					jugadaParejas = (emparejadasAnt ^ emparejada);
		  	   				}
		  	   				this.selectCards(jugadaParejas);
			  	   			this.model.setSelectedCardsMatched();
			  	   			
	  	  	  				this.numOfMatched_P2 = rmi.obtenerPuntosJ2();
			  	  			this.view.displayNumOfMatchedPairs_P2(this.numOfMatched_P2);
			  	  			
		  	   				jugadaResto = (jugadaParejas ^ seleccionadasJ2);
		  	   				this.selectCards(jugadaResto);
	  	   				}
	  	  	   			this.model.setSelectedCardsHiddenJ2();
		  	  			this.view.showInfoTurnPlayer1();
		  	  			if(this.rmi.isSolved()) {
	  	  	  				if(this.numOfMatched_P1 > this.numOfMatched_P2 ) {
	  	  						this.view.playerWin();
	  	  					}else if(this.numOfMatched_P1 == this.numOfMatched_P2){
	  	  						this.view.playerTie();
	  	  					}else{
	  	  						this.view.playerLose();
	  	  					}
	  	  				}
  	  	   				
  	  	   				
  	  	   				System.out.println("PAREAJAS ANTERIOR: "+String.format("%32s", Integer.toBinaryString(emparejadasAnt)).replace(' ', '0'));
  	  	   				System.out.println("PAREAJAS SIG: "+String.format("%32s", Integer.toBinaryString(emparejada)).replace(' ', '0'));
  	  	   				System.out.println("JUGADA PAREJAS: "+String.format("%32s", Integer.toBinaryString(jugadaParejas)).replace(' ', '0'));
  	  	   				System.out.println("JUGADA RESTO: "+String.format("%32s", Integer.toBinaryString(jugadaResto)).replace(' ', '0'));
  	  	   				/***********************************************/
  						turn.changeTurn();
  	  	  			}
  				}catch (RMIClientException e) {
					e.printStackTrace();
				}
  		}
  		
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Public Interface
	////////////////////////////////////////////////////////////////////////////
	
	/** This method initializes the board with a new set of cards **/
	 public void init() throws RMIClientException {
		this.resetBoardParam(rmi.obtenerPuntosJ1(), rmi.obtenerPuntosJ2());
		
		byte[][] tipos = rmi.obtenerTiposCartas();;
		this.model.reInitializeNewBoard(tipos);

		this.view.hiddenImages();
		
		int emparejadas = rmi.obtenerCartasEmparejadas();
		this.selectCards(emparejadas);
 		this.model.setSelectedCardsMatched();
 		
 		this.view.showInfoTurnPlayer1();
	 }	
	 
	////////////////////////////////////////////////////////////////////////////
	// Private Interface
	////////////////////////////////////////////////////////////////////////////
	private void resetBoardParam(byte p1, byte p2) {
		resetNumMatchedPairs(p1, p2);
	}
	
	private void resetNumMatchedPairs(byte p1, byte p2) {
		numOfMatched_P1 = p1;
		numOfMatched_P2 = p2;
		this.view.displayNumOfMatchedPairs_P1(numOfMatched_P1);
		this.view.displayNumOfMatchedPairs_P2(numOfMatched_P2);
	}
	
	private void selectCards (int jugada){
		int R1C1 = 0b0000000000000001;
		int R1C2 = 0b0000000000000010;
		int R1C3 = 0b0000000000000100;
		int R1C4 = 0b0000000000001000;
		
		int R2C1 = 0b0000000000010000;
		int R2C2 = 0b0000000000100000;
		int R2C3 = 0b0000000001000000;
		int R2C4 = 0b0000000010000000;
		
		int R3C1 = 0b0000000100000000;
		int R3C2 = 0b0000001000000000;
		int R3C3 = 0b0000010000000000;
		int R3C4 = 0b0000100000000000;
		
		int R4C1 = 0b0001000000000000;
		int R4C2 = 0b0010000000000000;
		int R4C3 = 0b0100000000000000;
		int R4C4 = 0b1000000000000000;
		
		
		if((jugada & R1C1) == R1C1) {
			this.model.setCardSelectedJ2(0, 0);
		}
		if((jugada & R1C2) == R1C2) {
			this.model.setCardSelectedJ2(0, 1);
		}
		if((jugada & R1C3) == R1C3) {
			this.model.setCardSelectedJ2(0, 2);
		}
		if((jugada & R1C4) == R1C4) {
			this.model.setCardSelectedJ2(0, 3);
		}
		
		if((jugada & R2C1) == R2C1) {
			this.model.setCardSelectedJ2(1, 0);
		}
		if((jugada & R2C2) == R2C2) {
			this.model.setCardSelectedJ2(1, 1);
		}
		if((jugada & R2C3) == R2C3) {
			this.model.setCardSelectedJ2(1, 2);
		}
		if((jugada & R2C4) == R2C4) {
			this.model.setCardSelectedJ2(1, 3);
		}
		
		if((jugada & R3C1) == R3C1) {
			this.model.setCardSelectedJ2(2, 0);
		}
		if((jugada & R3C2) == R3C2) {
			this.model.setCardSelectedJ2(2, 1);
		}
		if((jugada & R3C3) == R3C3) {
			this.model.setCardSelectedJ2(2, 2);
		}
		if((jugada & R3C4) == R3C4) {
			this.model.setCardSelectedJ2(2, 3);
		}
		
		if((jugada & R4C1) == R4C1) {
			this.model.setCardSelectedJ2(3, 0);
		}
		if((jugada & R4C2) == R4C2) {
			this.model.setCardSelectedJ2(3, 1);
		}
		if((jugada & R4C3) == R4C3) {
			this.model.setCardSelectedJ2(3, 2);
		}
		if((jugada & R4C4) == R4C4) {
			this.model.setCardSelectedJ2(3, 3);
		}
	}
	
}