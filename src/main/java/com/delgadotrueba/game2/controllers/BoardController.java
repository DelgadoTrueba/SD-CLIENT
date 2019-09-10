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

	//invoked when a button is pressed
	 /**
	  * This method is the action performed when a card is clicked it represents
	  * the main user interface of the game
	  * 
	  * @param e
	  *            an ActionEvent
	  */
	
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
		CellModel cellModel = this.model.mBoard[cellView.getRow()][cellView.getColumn()];
  		if (!this.model.isCardValid(cellModel)) {
  			return;
  		}

  		// Proceed with cases we want to cover
  		this.model.incrementSelectedCards();

  		if (this.model.getSelectedCards() <= BoardModel.getMaxSelectedCards()) {
  			this.model.setCardToVisible(cellView.getRow(), cellView.getColumn());
  			this.showCardImages();
  			this.model.mCardChecker[this.model.getSelectedCards() - 1] = this.model.getCellAtLoc(cellView.getRow(), cellView.getColumn());
  			this.model.addToChose(this.model.getCellAtLoc(cellView.getRow(), cellView.getColumn()));
  		}
  		if (this.model.getSelectedCards() == BoardModel.getMaxSelectedCards() ) {

  			if (!this.model.sameCellPosition()) {
///////////////////setSelectedCards(this.model.mCardChecker[FIRST], mCardChecker[SECOND]);
  				setSelectedCards(this.model.mCardChecker[0], this.model.mCardChecker[1]);
  			} else {
  				this.model.decrementSelectedCards();
  			}
	  } // if selectedCards == MAX
	
	}

	public void addModel(BoardModel m){
		this.model = m;
	}

	public void addView(BoardView v){
		this.view = v;
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
		this.setImages();
	
	 }	
	 
	 /** This method reinitializes the board with the current set of cards i.e. replay */
	 public void reInit() {
		 this.model.resetMatchedImages();
		 this.model.resetBoardParam();
		 this.peek();
		 this.setImages();
	 }
	 
	// This method check if any 2 selected cards are the same so it replaces
	// them with a blank cell or if they're different it flips them back,
	// it also check if the board is solved
	private void setSelectedCards(CellModel firstCell, CellModel secondCell) {

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
			showImage(getCellLocation(secondCell).x, getCellLocation(secondCell).y);
			peek();
			this.model.incrementNumOfMatchedPairs();
///////////////////////////////
			//finalMessage();
		} else {
	
			firstCell.setMatched(false);
			secondCell.setMatched(false);
			firstCell.setSelected(false);
			secondCell.setSelected(false);
			showImage(getCellLocation(secondCell).x, getCellLocation(secondCell).y);
			peek();
			this.model.incremenetNumOfFailedAttempts();
		}
		this.model.resetSelectedCards();
	}
	
	// This method gets the location of a cell on the board and returns that
	// specific point
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
			} // row for
		} // column for
		return null;
	}
	 
	// This method sets all the images on the board
		private void showCardImages() {
			// For each card on the board
			for (int row = 0; row < NUMBER_OF_ROWS; row++) {
				for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
			
					// Is card selected ?
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
						// The card was not selected
						showImage(row, column);
					
						String type = this.model.mCardStorage[column + (NUMBER_OF_COLUMNS * row)];
						int parsedType = Integer.parseInt(type);
					
						this.model.setCardType(row, column, parsedType);
				
					} 
				} 
			} 
		}
		
		// This method shows a specific image at a certain location
		private void showImage(int x, int y) {
			this.view.setImage(x, y,  this.model.mCardStorage[y + (NUMBER_OF_COLUMNS * x)]);
		}
		
		// This method delays the setCards method, so the user can peek at the cards
		// before the board resets them
		private static final int VISIBLE_DELAY = (int) 2 * 1000;
		private static final int PEEK_DELAY = (int) 2 * 1000;
		 
		private void peek() {

			Action showImagesAction = new AbstractAction() {
		
				private static final long serialVersionUID = 1L;
			
				public void actionPerformed(ActionEvent e) {
					showCardImages();
				}
			};
		
			Timer timer = new Timer(PEEK_DELAY, showImagesAction);
			timer.setRepeats(false);
			timer.start();
		}
		
		// This method sets the images on the board
		private void setImages() {
		
			for (int row = 0; row < NUMBER_OF_ROWS; row++) {
				for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {	
					this.view.setImage(row, column,  this.model.mCardStorage[column + (NUMBER_OF_COLUMNS * row)]);
				} // column loop
			} // row loop
		}
		
		
}
