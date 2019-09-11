package com.delgadotrueba.game2.controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.delgadotrueba.game2.errors.ErrorHandler;
import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.models.CellModel;
import com.delgadotrueba.game2.models.Model;
import javax.swing.Action;
import javax.swing.JButton;

import com.delgadotrueba.game2.views.BoardView;
import com.delgadotrueba.game2.views.CellView;

import javax.swing.Timer;
import com.delgadotrueba.game2.views.View;

public class BoardController implements java.awt.event.ActionListener {
	
	private static final int NUMBER_OF_ROWS = 4;
	private static final int NUMBER_OF_COLUMNS = 6;
	
	private BoardModel model;
	private BoardView view;

	public BoardController() {	
		// Must be empty
	} 
	
	public void addModel(BoardModel m){
		this.model = m;
	}

	public void addView(BoardView v){
		this.view = v;
	}

	//invoked when a button is pressed	
	public void actionPerformed(java.awt.event.ActionEvent e){

		if (e == null) {
		   ErrorHandler.error("BoardController: ", "actionPermormed(ActionEvent) received null", false);
		   return;
		}

		// Flush out cases where we don't care
		if (!(e.getSource() instanceof JButton)) {
			return;
		}

		CellView cellView = (CellView) e.getSource();
		CellModel cellModel = this.getCellModel(cellView);
		
  		if (!this.model.isCardValid(cellModel)) {
  			return;
  		}

  		// START GAME
  		this.model.incrementSelectedCards();

  		if ( this.model.isChoosable() ) {  			
  			this.setCardSelected(cellView.getRow(), cellView.getColumn());
  			this.model.saveCard(cellModel);
  		}

  		if ( this.model.isPlayable() ) {
  			if (!this.model.sameCellPosition()) {
  				CellModel firstCard = this.model.getFirst();
  				CellModel secondCard = this.model.getSecond();
  				play(firstCard, secondCard);
  			} else {
  				this.model.decrementSelectedCards();
  			}
  		}
	
	}
	
	// This method check if any 2 selected cards are the same: 
	// so it replaces them with a blank cell or if they're different it flips them back,
	// it also check if the board is solved
	private void play(CellModel firstCell, CellModel secondCell) {

		if (firstCell == null || secondCell == null) {
	
			if (firstCell == null) {
				ErrorHandler.error("BoardModel: ","setSelectedCards(Cell, Cell) received (null, ??)", true);
			}
			if (secondCell == null) {
				ErrorHandler.error("BoardModel: ", "setSelectedCards(Cell, Cell) received (??, null)", true);
			}
			return;
		}
	
		if (firstCell.sameType(secondCell)) {
			/**/
			Point cell = getCellLocation(secondCell);
			setCardSelected(cell.x, cell.y);
			setCardsMatched();
			if(model.isSolved()) {
					view.finalMessage();
			}
			/**/
			this.model.incrementNumOfMatchedPairs();
		} else {
			/**/
			Point cell = getCellLocation(secondCell);
			setCardSelected(cell.x, cell.y);
			setCardsHidden();
			/**/

			this.model.incremenetNumOfFailedAttempts();
		}
		this.model.resetSelectedCards();
	}

	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards){
		model.initModel(numOfMatchedPairs, numOfFailedAttempts, selectedCards);		
		this.init();
	}
	
	 /** This method initializes the board with a new set of cards*/
	 public void init() {
		this.model.initializeNewBoard();
		this.showImagesForce();
		this.hiddenImagesWithDelay();
	 }	
	 
	 /** This method reinitializes the board with the current set of cards i.e. replay */
	 public void reInit() {
		 this.model.reinitializeBoard();
		 this.showImagesForce();
		 this.hiddenImagesWithDelay();
	 }
	 
	// CHANGE MODEL AND REACT
	private void setCardSelected(int row, int column) {  			
			this.model.setCardSelected(row, column);
	}
	
	private void setCardsHidden() {
		CellModel cellModel1 = this.model.mCardChecker[0];
		CellModel cellModel2 = this.model.mCardChecker[1];
		
		Point p1 = this.getCellLocation(cellModel1);
		Point p2 = this.getCellLocation(cellModel2);
		
		this.model.setCardHidden(p1.x, p1.y);
		this.model.setCardHidden(p2.x, p2.y);
	}
	
	private void setCardsMatched() {
		CellModel cellModel1 = this.model.mCardChecker[0];
		CellModel cellModel2 = this.model.mCardChecker[1];
		
		Point p1 = this.getCellLocation(cellModel1);
		Point p2 = this.getCellLocation(cellModel2);
		
		this.model.setCardMatched(p1.x, p1.y);
		this.model.setCardMatched(p2.x, p2.y);
	}
	
	//START & RE-START A GAME
	private void hiddenImagesWithDelay() {

		Action showImagesAction = new AbstractAction() {
	
			private static final long serialVersionUID = 1L;
		
			public void actionPerformed(ActionEvent e) {
				hiddenImages();
			}
		};
	
		int PEEK_DELAY = (int) 2 * 1000;
		
		Timer timer = new Timer(PEEK_DELAY, showImagesAction);
		timer.setRepeats(false);
		timer.start();
	}
	
	private void hiddenImages() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				this.view.setHiddenImage(row, column);
			}
		}
	}
	
	private void showImagesForce() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {	
				CellModel cellmodel = this.model.mBoard[row][column];
				String type = cellmodel.getType();
				this.view.setImage(row, column, type);
			}
		}
	}
	
	//WRAPPER
	private Point getCellLocation(CellModel aCell) {

		if (aCell == null) {
			ErrorHandler.error("Board Controller","getCellLocation(Cell) received null", true);
			return null;
		}
	
		Point p = new Point();
	
		for (int column = 0; column < NUMBER_OF_ROWS; column++) {
			for (int row = 0; row < NUMBER_OF_COLUMNS; row++) {
				if (this.model.mBoard[column][row] == aCell) {
					p.setLocation(column, row);
					return p;
				}
			}
		}
		return null;
	}
	
	private CellModel getCellModel(CellView cellView) {
		return this.model.mBoard[cellView.getRow()][cellView.getColumn()];
	}
		
		
		
}
