package com.delgadotrueba.game2.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import com.delgadotrueba.game2.notifications.ActionsBoardModel;
import com.delgadotrueba.game2.notifications.BoardModelNotification;
import com.delgadotrueba.game2.utils.ErrorHandler;

public class BoardModel extends java.util.Observable {
			
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	private static final int MAX_SELECTED_CARDS = 2;
	
	// SIEMPRE DEBE DE DAR UNA MATRIZ PAR
	private int NUMBER_OF_ROWS;
	private int NUMBER_OF_COLUMNS;
	
	// GENERAR CELL TYPE => IMAGEN CORRESPONDIENTE
	private int MIN_NUM_OF_CARDS;
	private int MAX_NUM_OF_CARDS;
	private int NUMBER_OF_PAIRS;
	
	public CellModel[][] mBoard = null;
		
	public BoardModel(int rows, int cols) {
		NUMBER_OF_ROWS = rows;
		NUMBER_OF_COLUMNS = cols;
		
		MIN_NUM_OF_CARDS = 1;
		MAX_NUM_OF_CARDS = NUMBER_OF_ROWS * NUMBER_OF_COLUMNS;
		NUMBER_OF_PAIRS = MAX_NUM_OF_CARDS / 2;
		 
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
	
	////////////////////////////////////////////////////////////////////////////
	// Public Interface	 
	////////////////////////////////////////////////////////////////////////////
	
	/** This method initializes the board with a new set of cards*/
	public void initializeNewBoard() {
		this.resetMatchedAndSelectedAndTypeCards();
	}
	
	/** This method reinitializes the board with the current set of cards i.e. replay */
	public void reinitializeBoard() {
		 this.resetMatchedAndSelectedCards();
	}
		
	public boolean isCardValid(int row, int col) {
		// ES UNA POSICIÓN VALIDA DE LA MATRIZ
		if ( !coordenatesValid(row, col) ) {
			ErrorHandler.error("BoardModel Model", "isCardValid(Cell) received null", false);
			return false;
		}
		
		// NO ESTA SELECCIONADA NI EMPAREJADA
		CellModel aCard = this.mBoard[row][col];
		if ( aCard.isMatched() || aCard.isSelected() ) {
			return false;
		}
		return true;
	}
	
	public boolean areSelectedCardsSameType() {
		CellModel[] cellModelArray = new CellModel[MAX_SELECTED_CARDS];
		int pos = 0;
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if (mBoard[row][column].isSelected()) {
					cellModelArray[pos] = this.mBoard[row][column];
					pos++;
				}
			}
		}
		
		CellModel fisrtCard = cellModelArray[FIRST];
		CellModel secondCard = cellModelArray[SECOND];

		if ( !fisrtCard.sameType(secondCard) ) {
			return false;
		}
		return true;
	}
	
	public boolean isPlayable() {
		int numOfSelectedCards = 0;
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if (mBoard[row][column].isSelected()) {
					numOfSelectedCards++;
				}
			}
		}
		return numOfSelectedCards == MAX_SELECTED_CARDS;
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
	
	public void setCardSelected(int row, int col){
		CellModel cellModel = this.mBoard[row][col];
		cellModel.setMatched(false);
		cellModel.setSelected(true);
		
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setSelectedCard, this, row, col));
	}
	
	public void setSelectedCardsHidden() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if(mBoard[row][column].isSelected()) {
					CellModel cellModel = this.mBoard[row][column];
					cellModel.setMatched(false);
					cellModel.setSelected(false);
					
					setChanged();
					notifyObservers(new BoardModelNotification(ActionsBoardModel.setHiddenCard, this, row, column));
				}
			}
		}
	}
	
	public void setSelectedCardsMatched() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				if(mBoard[row][column].isSelected()) {
					CellModel cellModel = this.mBoard[row][column];
					cellModel.setMatched(true);
					cellModel.setSelected(false);
					
					setChanged();
					notifyObservers(new BoardModelNotification(ActionsBoardModel.setMatchedCard, this, row, column));
				}
			}
		}
	}
		
	////////////////////////////////////////////////////////////////////////////
	// Private Interface	 
	////////////////////////////////////////////////////////////////////////////
	
	private String[][] initCardStorage() {
		
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
		
		//return new String[][]{{"01", "01","02", "02", "03", "03"},{"04", "04", "05", "05","06", "06"},{ "07", "07", "08", "08", "09", "09"},{"10", "10", "11", "11","12", "12"}};
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
	
	private void resetMatchedAndSelectedCards() {
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				mBoard[row][column].setMatched(false);
				mBoard[row][column].setSelected(false);
			}
		}
	}
	
	private void resetMatchedAndSelectedAndTypeCards() {
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
	
	private boolean coordenatesValid(int row, int col) {
		if(row < 0 || row >= NUMBER_OF_ROWS || col < 0 || col >= NUMBER_OF_COLUMNS) {
			return false;
		}
		else{
			return true;
		}
	}
}