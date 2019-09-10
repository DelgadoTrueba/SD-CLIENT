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
  			
  			this.model.setCardToVisible(cellView.getRow(), cellView.getColumn());
  			//logica
  			this.showCardImages();
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
		
		//init
		this.init();
	}
	
	 /** This method initializes the board with a new set of cards*/
	 public void init() {
	 	this.model.resetMatchedImages();
		this.model.resetBoardParam();
		this.peek();
		this.model.mCardStorage = this.model.initCardStorage();
		this.showGameImages();
	
	 }	
	 
	 /** This method reinitializes the board with the current set of cards i.e. replay */
	 public void reInit() {
		 this.model.resetMatchedImages();
		 this.model.resetBoardParam();
		 this.peek();
		 this.showGameImages();
	 }
	 
	// This method check if any 2 selected cards are the same so it replaces
	// them with a blank cell or if they're different it flips them back,
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
			showGameImage(cell.x, cell.y);
			/**/
			peek();
			this.model.incrementNumOfMatchedPairs();
			if(this.model.isSolved()) {
				int numOfFailt = this.model.getNumOfFailedAttempts();
				int numOfCards = BoardModel.getMaxNumOfCards();
				this.view.finalMessage(numOfFailt, numOfCards);
			}
		} else {
			firstCell.setMatched(false);
			secondCell.setMatched(false);
			firstCell.setSelected(false);
			secondCell.setSelected(false);
			
			/**/
			Point cell = getCellLocation(secondCell);
			showGameImage(cell.x, cell.y);
			/**/
			
			peek();
			this.model.incremenetNumOfFailedAttempts();
		}
		this.model.resetSelectedCards();
	}
	 
	// This method sets all the images on the board
	private void showCardImages() {
		// For each card on the board
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
		
				if (!this.model.mBoard[row][column].isSelected()) {
					// If selected, verify if the card was matched by the user
					if (this.model.mBoard[row][column].isMatched()) {
						// It was matched, empty the card slot
						this.view.setEmptyImage(row, column);
						this.model.setEmptyCardType(row, column);
					} else {
						// It was not, put the "hidden card" image
						this.view.setHiddenImage(row, column);
						this.model.setHiddenCardType(row, column);
					}
				} else {
					String type = this.model.mCardStorage[column + (NUMBER_OF_COLUMNS * row)];
					
					this.view.setImage(row, column, type);
					this.model.setCardType(row, column, type);
				} 
			} 
		} 
	}
	
	
	// This method delays the setCards method, so the user can peek at the cards before the board resets them
	private void peek() {

		Action showImagesAction = new AbstractAction() {
	
			private static final long serialVersionUID = 1L;
		
			public void actionPerformed(ActionEvent e) {
				showCardImages();
			}
		};
	
		int PEEK_DELAY = (int) 2 * 1000;
		
		Timer timer = new Timer(PEEK_DELAY, showImagesAction);
		timer.setRepeats(false);
		timer.start();
	}
	
	// This method sets the images on the board
	private void showGameImages() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {	
				String num = this.model.mCardStorage[column + (NUMBER_OF_COLUMNS * row)];
				this.view.setImage(row, column, num);
			}
		}
	}
	
	private void showGameImage(int row, int column) {
		String num = this.model.mCardStorage[column + (NUMBER_OF_COLUMNS * row)];
		this.view.setImage(row, column, num);
	}
	
	// This method gets the location of a cell on the board and returns that specific point
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
