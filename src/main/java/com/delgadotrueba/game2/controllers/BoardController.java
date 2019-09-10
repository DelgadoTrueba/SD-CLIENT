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
  			
  			this.model.selectCard(cellView.getRow(), cellView.getColumn());
  			this.setCardVisible(cellView.getRow(), cellView.getColumn());
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

	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards){
		model.initModel(numOfMatchedPairs, numOfFailedAttempts, selectedCards);		
		this.init();
	}
	
	 /** This method initializes the board with a new set of cards*/
	 public void init() {
	 	this.model.resetMatchedImages();
		this.model.resetBoardParam();
		this.model.mCardStorage = this.model.initCardStorage();
		this.showImagesForce();
		this.hiddenImagesWithDelay();
	 }	
	 
	 /** This method reinitializes the board with the current set of cards i.e. replay */
	 public void reInit() {
		 this.model.resetMatchedImages();
		 this.model.resetBoardParam();
		 this.showImagesForce();
		 this.hiddenImagesWithDelay();
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
	
			firstCell.setMatched(true);
			secondCell.setMatched(true);
			firstCell.setSelected(false);
			secondCell.setSelected(false);
			
			/**/
			Point cell = getCellLocation(secondCell);
			setCardVisible(cell.x, cell.y);
			setCardsEmpty();
			if(model.isSolved()) {
					view.finalMessage();
			}
			/**/
			this.model.incrementNumOfMatchedPairs();
		} else {
			firstCell.setMatched(false);
			secondCell.setMatched(false);
			firstCell.setSelected(false);
			secondCell.setSelected(false);
			
			/**/
			Point cell = getCellLocation(secondCell);
			setCardVisible(cell.x, cell.y);
			setCardsEmpty();
			setCardsHidden();
			/**/

			this.model.incremenetNumOfFailedAttempts();
		}
		this.model.resetSelectedCards();
	}
	 
	// CHANGE MODEL AND REACT
	private void setCardVisible(int row, int column) {
			String type = this.model.mCardStorage[column + (NUMBER_OF_COLUMNS * row)];
			this.model.setCardType(row, column, type);
	}
	
	private void setCardsHidden() {
		CellModel cellModel1 = this.model.mCardChecker[0];
		CellModel cellModel2 = this.model.mCardChecker[1];
		
		Point p1 = this.getCellLocation(cellModel1);
		Point p2 = this.getCellLocation(cellModel2);
		
		this.model.setHiddenCardType(p1.x, p1.y);
		this.model.setHiddenCardType(p2.x, p2.y);
	}
	
	private void setCardsEmpty() {
		CellModel cellModel1 = this.model.mCardChecker[0];
		CellModel cellModel2 = this.model.mCardChecker[1];
		
		Point p1 = this.getCellLocation(cellModel1);
		Point p2 = this.getCellLocation(cellModel2);
		
		this.model.setEmptyCardType(p1.x, p1.y);
		this.model.setEmptyCardType(p2.x, p2.y);
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
				String num = this.model.mCardStorage[column + (NUMBER_OF_COLUMNS * row)];
				this.view.setImage(row, column, num);
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
