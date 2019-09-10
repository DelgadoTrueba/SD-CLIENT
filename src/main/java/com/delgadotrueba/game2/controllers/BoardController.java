package com.delgadotrueba.game2.controllers;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.delgadotrueba.game2.errors.ErrorHandler;
import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.models.Model;
import javax.swing.Action;
import com.delgadotrueba.game2.views.BoardView;
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
	public void actionPerformed(java.awt.event.ActionEvent e){
		//model.incrementValue();
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
