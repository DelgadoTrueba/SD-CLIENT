package com.delgadotrueba.game2.controllers;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.utils.ErrorHandler;
import com.delgadotrueba.game2.utils.Turn;
import com.delgadotrueba.game2.views.BoardView;
import com.delgadotrueba.game2.views.CellView;

public class BoardController implements java.awt.event.ActionListener {
	
	private BoardModel model;
	private BoardView view;
		
	private int numOfMatched_P1 = 0;
	
	private Turn turn;
	
	public BoardController(Turn turn) {	
		this.turn = turn;
		// Must be empty
	} 
	
	public void addModel(BoardModel m){
		this.model = m;
	}

	public void addView(BoardView v){
		this.view = v;
	}
	
	public void initModel(){		
		this.init();
	}

	//invoked when a button is pressed	
	public void actionPerformed(java.awt.event.ActionEvent e){

		if (e == null) {
		   ErrorHandler.error("BoardController: ", "actionPermormed(ActionEvent) received null", false);
		   return;
		}
		
		// Flush out cases where we don't care
		if (!(e.getSource() instanceof CellView)) {
			return;
		}
		
  		// START GAME
		if (!(turn.isPlayerOne())) {
			this.view.notIsYourTurn();
			return;
		}
		
		CellView cellView = (CellView) e.getSource();
		int row = cellView.getRow();
		int col = cellView.getColumn();
		
  		if (!this.model.isCardValid(row, col)) {
  			return;
  		}
  		
  		this.model.setCardSelected(row, col);


  		if ( this.model.isPlayable() ) {
  			
  			if (this.model.areSelectedCardsSameType()){
  				
  				this.model.setSelectedCardsMatched();
  				
  				this.incrementNumOfMatchedPairs();
  		  		
  				if(this.model.isSolved()) {
  					this.view.player1Win();
  				}
  				
  			} else {
  				  				
  				this.model.setSelectedCardsHidden();
  				
  				this.turn.changeTurn();
  				this.view.showInfoTurnPlayer2();
  			}
  			
  		}
  		
	}
	
	 /** This method initializes the board with a new set of cards*/
	 public void init() {
		this.resetBoardParam();
		this.model.initializeNewBoard();
		this.view.hiddenImages();
		
		if ((turn.isPlayerTwo())) {
			this.view.showInfoTurnPlayer2();
		}
	 }	
	 
	 /** This method reinitializes the board with the current set of cards i.e. replay */
	 public void reInit() {
		this.resetBoardParam();
		this.model.reinitializeBoard();
		this.view.hiddenImages();

		if ((turn.isPlayerTwo())) {
			this.view.showInfoTurnPlayer2();
		}
	 }
	 	// PRIVATE API
	
	private void resetBoardParam() {
		resetNumMatchedPairs();
	}
	
	private void resetNumMatchedPairs() {
		numOfMatched_P1 = 0;
		this.view.displayNumOfMatchedPairs_P1(numOfMatched_P1);
	}
	public void incrementNumOfMatchedPairs() {
		if(turn.isPlayerOne()) {
			this.numOfMatched_P1 = this.numOfMatched_P1 +1 ;
			this.view.displayNumOfMatchedPairs_P1(numOfMatched_P1);
		}	
	}
	
	
}