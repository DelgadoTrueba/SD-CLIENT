package com.delgadotrueba.game2.controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.models.CellModel;
import com.delgadotrueba.game2.models.Model;
import com.delgadotrueba.game2.utils.CellWrapper;
import com.delgadotrueba.game2.utils.ErrorHandler;
import com.delgadotrueba.game2.utils.Turn;

import javax.swing.Action;
import javax.swing.JButton;

import com.delgadotrueba.game2.views.BoardView;
import com.delgadotrueba.game2.views.CellView;
import com.delgadotrueba.game2.views.NewBoardButton;
import com.delgadotrueba.game2.views.RetryButton;

import javax.swing.Timer;
import com.delgadotrueba.game2.views.View;

public class BoardController implements java.awt.event.ActionListener {
	
	private BoardModel model;
	private BoardView view;
	
	private static final int NUMBER_OF_ROWS = 3;
	private static final int NUMBER_OF_COLUMNS = 2;
	
	private int numOfMatched_P1 = 0;
	private int numOfMatched_P2 = 0;
	
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
	
	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards){
		//model.initModel(numOfMatchedPairs, numOfFailedAttempts, selectedCards);		
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
  		  		
  				this.model.isSolved();
  			} else {
  				  				
  				this.model.setSelectedCardsHidden();
  				
  				this.view.showFail();
  				this.turn.changeTurn();
  			}
  			
  		}
  		
	}
	
	 /** This method initializes the board with a new set of cards*/
	 public void init() {
		this.resetBoardParam();
		this.model.initializeNewBoard();
		this.view.hiddenImages();
		this.view.showTurn();
	 }	
	 
	 /** This method reinitializes the board with the current set of cards i.e. replay */
	 public void reInit() {
		this.resetBoardParam();
		this.model.reinitializeBoard();
		this.view.hiddenImages();

		this.view.showTurn();
	 }
	 	// PRIVATE API
	
	private void resetBoardParam() {
		resetNumMatchedPairs();
	}
	
	private void resetNumMatchedPairs() {
		numOfMatched_P1 = 0;
		numOfMatched_P2 = 0;
		this.view.displayNumOfMatchedPairs_P1(numOfMatched_P1);
		this.view.displayNumOfMatchedPairs_P2(numOfMatched_P2);
	}
	public void incrementNumOfMatchedPairs() {
		if(turn.isPlayerOne()) {
			this.numOfMatched_P1 = this.numOfMatched_P1 +1 ;
			this.view.displayNumOfMatchedPairs_P1(numOfMatched_P1);
		}	
	}
	
	
}