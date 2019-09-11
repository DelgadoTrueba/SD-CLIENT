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

import javax.swing.Action;
import javax.swing.JButton;

import com.delgadotrueba.game2.views.BoardView;
import com.delgadotrueba.game2.views.CellView;

import javax.swing.Timer;
import com.delgadotrueba.game2.views.View;

public class BoardController implements java.awt.event.ActionListener {
	
	private BoardModel model;
	private BoardView view;
	
	private static final int NUMBER_OF_ROWS = 4;
	private static final int NUMBER_OF_COLUMNS = 6;
	
	private static final int MAX_SELECTED_CARDS = 2;
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	
	private int numSelectedCards = 0;
	public CellWrapper[] selectedCards = new CellWrapper[MAX_SELECTED_CARDS];

	private int numOfMatchedPairs = 0;
	private int numOfFailedAttempts = 0;
	
	public BoardController() {	
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

		CellView cellView = (CellView) e.getSource();
		CellModel cellModel = this.model.getCellModel(cellView.getRow(), cellView.getColumn());
		
  		if (!this.model.isCardValid(cellModel)) {
  			return;
  		}
  		// START GAME
  		this.numSelectedCards = this.numSelectedCards + 1;

  		if ( this.numSelectedCards <= MAX_SELECTED_CARDS ) {  	
  			int row = cellView.getRow();
  	  		int col = cellView.getColumn();
  	  		
  			this.model.setCardSelected(row, col);
  			
  			this.saveCard(cellModel, cellView);
  		}

  		if ( this.numSelectedCards == MAX_SELECTED_CARDS ) {
  			CellWrapper firstCard = this.selectedCards[FIRST];
  			CellWrapper secondCard = this.selectedCards[SECOND];
  			
  			CellModel firstCellModel = firstCard.getCellModel();
  			CellModel secondCellModel = secondCard.getCellModel();
  			
  			if (firstCellModel.sameType(secondCellModel)) {
  				
  				this.model.setCardMatched(firstCard.getRow(), firstCard.getColumn());
  				this.model.setCardMatched(secondCard.getRow(), secondCard.getColumn());
  				
  				this.incrementNumOfMatchedPairs();
  				
  				if(model.isSolved()) {
  					view.finalMessage();
  				}
  			} else {
  				
  				this.model.setCardHidden(firstCard.getRow(), firstCard.getColumn());
  				this.model.setCardHidden(secondCard.getRow(), secondCard.getColumn());
  				
  				this.incremenetNumOfFailedAttempts();
  			}
  			
  			this.numSelectedCards = 0;
  		}
	}
	
	 /** This method initializes the board with a new set of cards*/
	 public void init() {
		this.resetBoardParam();
		this.model.initializeNewBoard();
	 }	
	 
	 /** This method reinitializes the board with the current set of cards i.e. replay */
	 public void reInit() {
		this.resetBoardParam();
		this.model.reinitializeBoard();
	 }
	 	// PRIVATE API
	
		
	public void saveCard(CellModel cellModel, CellView cellView) {
		this.selectedCards[this.numSelectedCards - 1] = new CellWrapper(cellModel, cellView);
	}
	
	private void resetBoardParam() {
		resetFailedAttempts();
		resetNumMatchedPairs();
	}
	
	private void resetNumMatchedPairs() {
		numOfMatchedPairs = 0;
		this.view.displayNumOfMatchedPairs(this.numOfMatchedPairs);
	}
	public void incrementNumOfMatchedPairs() {
		this.numOfMatchedPairs = this.numOfMatchedPairs +1 ;
		this.view.displayNumOfMatchedPairs(this.numOfMatchedPairs);
	}
	
	private void resetFailedAttempts() {
		numOfFailedAttempts = 0;
		this.view.displayNumOfFailedAttempts(this.numOfFailedAttempts);
	}
	public void incremenetNumOfFailedAttempts() {
		this.numOfFailedAttempts = this.numOfFailedAttempts + 1;
		this.view.displayNumOfFailedAttempts(this.numOfFailedAttempts);
	}
	
	//WRAPPER
			
}