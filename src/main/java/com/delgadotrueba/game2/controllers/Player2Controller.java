package com.delgadotrueba.game2.controllers;

import com.delgadotrueba.game2.RMI.BoardAlreadyCreatedException;
import com.delgadotrueba.game2.RMI.BoardNotInitializedException;
import com.delgadotrueba.game2.RMI.BoardRMI;
import com.delgadotrueba.game2.RMI.CardNotValidException;
import com.delgadotrueba.game2.RMI.NotIsYourTurnException;
import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.notifications.ObservableBoarModel;
import com.delgadotrueba.game2.utils.ErrorHandler;
import com.delgadotrueba.game2.utils.Turn;
import com.delgadotrueba.game2.views.BoardView;
import com.delgadotrueba.game2.views.CellView;

public class Player2Controller implements java.awt.event.ActionListener {
	
	private ObservableBoarModel model;
	private BoardView view;
	
	private int numOfMatched = 0;
	
	private BoardRMI rmi;
	private static final boolean PLAYER_TWO = true;

	
	public Player2Controller(BoardRMI rmi) {	
		this.rmi = rmi;
		// Must be empty
	} 
	
	public void addModel(ObservableBoarModel m){
		this.model = m;
	}

	public void addView(BoardView v){
		this.view = v;
	}
	
	public void initModel(){		
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
		try {
			if (!(rmi.getTurn().isPlayerTwo())) {
				this.view.notIsYourTurn();
				return;
			}
		} catch (BoardNotInitializedException e1) {
			System.out.println("BoardNotInitializedException");
			return;
		}
		
		CellView cellView = (CellView) event.getSource();
		int row = cellView.getRow();
		int col = cellView.getColumn();
		
  		if (!this.model.isCardValid(row, col)) {
  			return;
  		}
  		
  		this.model.setCardSelected(row, col);

  		if ( this.model.isPlayable() ) {

  			int[][] resul = this.model.getSelectedCards();
  			
  			int r1 = resul[0][0];
  			int c1 = resul[0][1];

  			int r2 = resul[1][0];
  			int c2 = resul[1][1];
  			
  			try {
  				if (this.rmi.matchCards(PLAYER_TWO, r1, c1, r2, c2)){
  	  				
  	  				this.model.setSelectedCardsMatched();
  	  				
  	  				this.view.displayNumOfMatchedPairs_P1(++numOfMatched);	
  	  		  		
  	  				if(this.rmi.isSolved()) {
  	  					this.view.playerWin();
  	  				}
  	  				
  	  			} else {
  	  				  				
  	  				this.model.setSelectedCardsHidden(); 				
  	   				this.view.showInfoTurnPlayer1();
  	  			}
  			}catch (BoardNotInitializedException e) {
				System.out.println("BoardNotInitializedException");
			}catch (NotIsYourTurnException e) {
				System.out.println("NotIsYourTurnException");
			}catch (CardNotValidException e) {
				System.out.println("CardNotValidException");
			}
  			
  			
  			
  		}
  		
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Public Interface
	////////////////////////////////////////////////////////////////////////////
	
	/** This method initializes the board with a new set of cards*/
	 public void init() {
		
		try {
			this.resetBoardParam();
			this.view.hiddenImages();
			if ((this.rmi.getTurn().isPlayerTwo())) {
				this.view.showInfoTurnPlayer2();
			}
		}catch (BoardNotInitializedException e) {
			System.out.println("BoardNotInitializedException");
		}
				
	 }	
	 
	////////////////////////////////////////////////////////////////////////////
	// Private Interface
	////////////////////////////////////////////////////////////////////////////
	private void resetBoardParam() {
		resetNumMatchedPairs();
	}
	
	private void resetNumMatchedPairs() {
		numOfMatched = 0;
		this.view.displayNumOfMatchedPairs_P1(numOfMatched);
	}
	
}