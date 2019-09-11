package com.delgadotrueba.game2.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.delgadotrueba.game2.notifications.ActionsBoardModel;
import com.delgadotrueba.game2.notifications.BoardModelNotification;
import com.delgadotrueba.game2.errors.ErrorHandler;

public class BoardModel extends java.util.Observable {
	
	private static final int NUMBER_OF_ROWS = 4;
	private static final int NUMBER_OF_COLUMNS = 6;
	
	private static final int NUMBER_OF_PAIRS = 12;
	private static final int MAX_NUM_OF_CARDS = 24;
	
	private static final int MIN_NUM_OF_CARDS = 1;
	private static final int MAX_SELECTED_CARDS = 2;
		
	// Card types
	private static final String HIDDEN_CARD_TYPE = "26";
	private static final String EMPTY_CARD_TYPE = "25";

	private int numOfMatchedPairs = 0;
	private int numOfFailedAttempts = 0;
	
	public CellModel[][] mBoard = null;
		
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	public CellModel[] mCardChecker = new CellModel[MAX_SELECTED_CARDS];
	private int selectedCards = 0;
	public ArrayList<CellModel> chosenCards = new ArrayList<CellModel>();
	
	public BoardModel() {
		mBoard = new CellModel[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		
		String[][] typeCell = initCardStorage();
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			   for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				   String type = typeCell[row][column];
				   mBoard[row][column] = new CellModel(type);
			   }
		}
	}
		
	public void initModel(int numOfMatchedPairs, int numOfFailedAttempts, int selectedCards ) {
		//JButton[][] btnBoard;
	}
	
	/** This method initializes the board with a new set of cards*/
	public void initializeNewBoard() {
		this.resetBoardParam();
		this.resetMatchedAndSelectedAndTypeImages();
	}
	
	/** This method reinitializes the board with the current set of cards i.e. replay */
	public void reinitializeBoard() {
		 this.resetBoardParam();
		 this.resetMatchedAndSelectedImages();
	}
	
	/*PRIVATE*/
	private void resetBoardParam() {
		resetFailedAttempts();
		resetNumMatchedCards();
	}

	private void resetMatchedAndSelectedImages() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				mBoard[row][column].setMatched(false);
				mBoard[row][column].setSelected(false);
			}
		}
	}
	
	private void resetMatchedAndSelectedAndTypeImages() {
		String[][] typeCell = initCardStorage();
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				mBoard[row][column].setMatched(false);
				mBoard[row][column].setSelected(false);
				String type = typeCell[row][column];
				mBoard[row][column].setType(type);
			}
		}
	}
	
	private String[][] initCardStorage() {
		/*
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
		
		String[][] typeCell = new String[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
				
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				typeCell[row][column] = cardStorage[column + (NUMBER_OF_COLUMNS * row)];
			}
		}
	
		return typeCell;
		*/
		return new String[][]{{"01", "01","02", "02", "03", "03"},{"04", "04", "05", "05","06", "06"},{ "07", "07", "08", "08", "09", "09"},{"10", "10", "11", "11","12", "12"}};
	}
	
	private String[] randomListWithoutRep() {

		String[] generatedArray = new String[NUMBER_OF_PAIRS];
		ArrayList<String> generated = new ArrayList<String>();
	
		for (int i = 0; i < NUMBER_OF_PAIRS; i++) {
			while (true) {
				String next = generateRandomImageType(MAX_NUM_OF_CARDS, MIN_NUM_OF_CARDS);
			
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

	private String generateRandomImageType(int max, int min) {

		Random random = new Random();
		Integer aNumber = (min + random.nextInt(max));
	
		if (aNumber > 0 && aNumber < 10) {
			return "0" + aNumber;
		} else {
			return aNumber.toString();
		}
	}
	
	/*PUBLIC*/
	public void saveCard(CellModel cellModel) {
		this.mCardChecker[this.selectedCards - 1] = cellModel;
		this.addToChose(cellModel);
	}
	
	public boolean sameCellPosition() {
		CellModel firstCell = this.mCardChecker[FIRST]; 
		CellModel secondCell = this.mCardChecker[SECOND];

		if (firstCell == null || secondCell == null) {
			if (secondCell == firstCell) {
				// They're equal if both are null
				return true;
			}
	
			if (firstCell == null) {
				ErrorHandler.error("BoardModel: ", "sameCellPosition(Point, Point) received (null, ??)", true);
			}
			if (secondCell == null) {
				ErrorHandler.error("BoardModel: ", "sameCellPosition(Point, Point) received (??, null)", true);
			}
		
			return false;
		}
	
		if (firstCell.equals(secondCell)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isCardValid(CellModel aCard) {
		if (aCard == null) {
			ErrorHandler.error("Cell Model", "isCardValid(Cell) received null", false);
			return false;
		}
		if ( aCard.isMatched() || aCard.isSelected() ) {
			return false;
		}
		
		return true;
	}
	
	public boolean isSolved() {	
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if (!mBoard[row][column].isMatched()) {
					return false;
				}
			}
		}
		return true;	
	}
	
	public boolean isPlayable() {
		return this.selectedCards == MAX_SELECTED_CARDS;
	}
	
	public boolean isChoosable() {
		return this.selectedCards <= MAX_SELECTED_CARDS;
	}

	public void addToChose(CellModel aCard) {

		if (aCard != null) {
			if (!chosenCards.contains(aCard)) {
				chosenCards.add(aCard);
			}
		} else {
			ErrorHandler.error("Board Model","addToChose( Cell ) received null.", true);
		}
	}
	
	public CellModel getFirst() {
		return this.mCardChecker[FIRST];
	}
	
	public CellModel getSecond() {
		return this.mCardChecker[SECOND];
	}
	
	public CellModel getCellAtLoc(int row, int col) {
		return mBoard[row][col];
	}
	
	/*OBSERBABLES NOTIFY*/	
	private void resetNumMatchedCards() {
		numOfMatchedPairs = 0;
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setNumOfMatchedPairs, this));
	}
	public void incrementNumOfMatchedPairs() {
		this.numOfMatchedPairs = this.numOfMatchedPairs +1 ;
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setNumOfMatchedPairs, this));
	}
	
	private void resetFailedAttempts() {
		numOfFailedAttempts = 0;
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setNumOfFailedAttempts, this));
	}
	public void incremenetNumOfFailedAttempts() {
		this.numOfFailedAttempts = this.numOfFailedAttempts + 1;
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setNumOfFailedAttempts, this));
	}

	public void incrementSelectedCards() {
		this.selectedCards = this.selectedCards + 1;
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setSelectedCards, this));
	}
	public void decrementSelectedCards() {
		this.selectedCards = this.selectedCards - 1;
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setSelectedCards, this));
	}
	public void resetSelectedCards() {
		selectedCards = 0;
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setSelectedCards, this));
	}
	
	public void setCardHidden(int row, int col){
		CellModel cellModel = this.mBoard[row][col];
		cellModel.setMatched(false);
		cellModel.setSelected(false);
		
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setHiddenCard, this, row, col));
	}
	
	public void setCardSelected(int row, int col){
		CellModel cellModel = this.mBoard[row][col];
		cellModel.setMatched(false);
		cellModel.setSelected(true);
		
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setSelectedCard, this, row, col));
	}
	
	public void setCardMatched(int row, int col){
		CellModel cellModel = this.mBoard[row][col];
		cellModel.setMatched(true);
		cellModel.setSelected(false);
		
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setMatchedCard, this, row, col));
	}

	
	
	//GETTERS
	public int getNumOfMatchedPairs() {
		return numOfMatchedPairs;
	}
	
	public int getNumOfFailedAttempts() {
		return numOfFailedAttempts;
	}
	
	public int getSelectedCards() {
		return selectedCards;
	}
		
}