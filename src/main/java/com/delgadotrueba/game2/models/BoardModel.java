package com.delgadotrueba.game2.models;

import javax.swing.JButton;

import com.delgadotrueba.game2.notifications.ActionsBoardModel;
import com.delgadotrueba.game2.notifications.ActionsModel;
import com.delgadotrueba.game2.notifications.BoardModelNotification;
import com.delgadotrueba.game2.notifications.ModelNotification;

public class BoardModel extends java.util.Observable {
	
	private static final int NUMBER_OF_ROWS = 4;
	private static final int NUMBER_OF_COLUMNS = 6;
	
	private CellModel[][] mBoard = null;
	
	private int numOfMatchedPairs = 0;
	private int numOfFailedAttempts = 0;
	private int selectedCards = 0;
	
	public BoardModel() {
		mBoard = new CellModel[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
	}
	
	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards ) {
		//JButton[][] btnBoard;
		this.setNumOfMatchedPairs(numOfMatchedPairs);
		this.setNumOfFailedAttempts(numOfFailedAttempts);
		this.setSelectedCards(selectedCards);
	}
	
	// this method resets all the matched images, used in the replay method and new game
	private void resetMatchedImages() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if (mBoard[row][column].isMatched()) {
					mBoard[row][column].setMatched(false);
				}
			}
		}
	}
	
	
	// This method resets the number of selected cards to 0 after 2 cards have been chosen and checked
	private void resetSelectedCards() {
		selectedCards = 0;
	}
	
	// This method resets the number of matched pairs on the board
	private void resetNumMatchedCards() {
		numOfMatchedPairs = 0;
	}

	// This method resets the number of failed attempts
	private void resetFailedAttempts() {
		numOfFailedAttempts = 0;
	}

	// This method resets the parameters of the board used when replaying or when starting a new game
	private void resetBoardParam() {
		resetFailedAttempts();
		resetNumMatchedCards();
	}

	
	public int getNumOfMatchedPairs() {
		return numOfMatchedPairs;
	}

	public void setNumOfMatchedPairs(int numOfMatchedPairs) {
		this.numOfMatchedPairs = numOfMatchedPairs;
		setChanged();
		notifyObservers(
				new BoardModelNotification(ActionsBoardModel.setNumOfMatchedPairs, this)
				);
	}

	public int getNumOfFailedAttempts() {
		return numOfFailedAttempts;
	}

	public void setNumOfFailedAttempts(int numOfFailedAttempts) {
		this.numOfFailedAttempts = numOfFailedAttempts;
		setChanged();
		notifyObservers(
				new BoardModelNotification(ActionsBoardModel.setNumOfFailedAttempts, this)
				);
	}

	public int getSelectedCards() {
		return selectedCards;
	}

	public void setSelectedCards(int selectedCards) {
		this.selectedCards = selectedCards;
		setChanged();
		notifyObservers(
				new BoardModelNotification(ActionsBoardModel.setSelectedCards, this)
				);
	}

	
}
