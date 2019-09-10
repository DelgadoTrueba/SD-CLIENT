package com.delgadotrueba.game2.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JButton;

import com.delgadotrueba.game2.notifications.ActionsBoardModel;
import com.delgadotrueba.game2.notifications.ActionsModel;
import com.delgadotrueba.game2.notifications.BoardModelNotification;
import com.delgadotrueba.game2.notifications.ModelNotification;

public class BoardModel extends java.util.Observable {
	
	private static final int NUMBER_OF_ROWS = 4;
	private static final int NUMBER_OF_COLUMNS = 6;
	
	private static final int NUMBER_OF_PAIRS = 12;
	private static final int MAX_NUM_OF_CARDS = 24;
	private static final int MIN_NUM_OF_CARDS = 1;
	
	// Card types
	private static final int EMPTY_CELL_TYPE = 0;
	private static final int HIDDEN_CARD_TYPE = 26;
	private static final int EMPTY_CARD_TYPE = 25;

	
	public CellModel[][] mBoard = null;
	
	public String[] mCardStorage;
	
	private int numOfMatchedPairs = 0;
	private int numOfFailedAttempts = 0;
	private int selectedCards = 0;
	
	public BoardModel() {
		mCardStorage = initCardStorage();
		mBoard = new CellModel[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			   for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				   mBoard[row][column] = new CellModel(EMPTY_CELL_TYPE);
				   //mBoard[row][column].setSelected(true);
			   }
	  }
	}
	
	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards ) {
		//JButton[][] btnBoard;
		this.setNumOfMatchedPairs(numOfMatchedPairs);
		this.setNumOfFailedAttempts(numOfFailedAttempts);
		this.setSelectedCards(selectedCards);
	}
	
	
	public void setEmptyCardType(int row, int col) {
		mBoard[row][col].setType(EMPTY_CARD_TYPE);
	}
	
	public void setHiddenCardType(int row, int col) {
		mBoard[row][col].setType(HIDDEN_CARD_TYPE);
	}
	
	public void setCardType(int row, int col, int type) {
		mBoard[row][col].setType(type);
	}
	
	// This method creates an array of string holding the indices of 24 random
	// images grouped in pairs.

	private String[] initCardStorage() {

		String[] cardStorage = new String[MAX_NUM_OF_CARDS];
		String[] firstPair = new String[NUMBER_OF_PAIRS];
		String[] secondPair = new String[NUMBER_OF_PAIRS];
	
		firstPair = randomListWithoutRep();
	
		for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
			cardStorage[i] = firstPair[i];
		}
	
		Collections.shuffle(Arrays.asList(firstPair));
	
		for (int j = 0; j < NUMBER_OF_PAIRS; j++) {
			secondPair[j] = firstPair[j];
		}
	
		for (int k = NUMBER_OF_PAIRS; k < MAX_NUM_OF_CARDS; k++) {
			cardStorage[k] = secondPair[k - NUMBER_OF_PAIRS];
		}
	
		return cardStorage;
	}
	
	// this method is to generate a list of NUMBER_OF_PAIRS images (types)
	// without repetition

	private String[] randomListWithoutRep() {

		String[] generatedArray = new String[NUMBER_OF_PAIRS];
		ArrayList<String> generated = new ArrayList<String>();
	
		for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
			while (true) {
				String next = generateRandomImageFilename(MAX_NUM_OF_CARDS, MIN_NUM_OF_CARDS);
			
				if (!generated.contains(next)) {
					generated.add(next);
					generatedArray[i] = generated.get(i);
					break; // breaks back to "for" loop
				}
			} // inner loop - for every random card, ensure its not already
			// existing
		} // outer loop - we want NUMBER_OF_PAIRS different pairs
	
		return generatedArray;
	}
	
	// This method generates a random image, i.e. a random integer representing
	// the type of the image

	private String generateRandomImageFilename(int max, int min) {

		Random random = new Random();
		Integer aNumber = (min + random.nextInt(max));
	
		if (aNumber > 0 && aNumber < 10) {
			return "0" + aNumber;
		} else {
			return aNumber.toString();
		}
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

	public CellModel[][] getmBoard() {
		return mBoard;
	}

	public void setmBoard(CellModel[][] mBoard) {
		this.mBoard = mBoard;
	}
	

	
}
