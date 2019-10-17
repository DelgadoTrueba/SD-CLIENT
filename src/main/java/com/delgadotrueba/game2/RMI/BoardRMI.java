package com.delgadotrueba.game2.RMI;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.utils.Turn;

public class BoardRMI {

	private Turn turn;
	private BoardModel boardModel;

	private boolean full;
	
	private static final boolean PLAYER_ONE = false;
	private static final boolean PLAYER_TWO = true;
	
	private final int ROWS;
	private final int COLS;

	
	public BoardRMI(int rows, int cols) {
		this.turn = null;
		this.boardModel = null;
		
		this.full = false;
		
		this.ROWS = rows;
		this.COLS = cols;
	}
	
	public void initializeNewBoard() throws BoardAlreadyCreatedException {
		
		if(this.turn != null && this.boardModel != null) {
			throw new BoardAlreadyCreatedException();
		}
		
		this.boardModel = new BoardModel(ROWS, COLS);
		this.boardModel.initializeNewBoard();
		
		this.turn = new Turn(false, true);
	}
	
	public Turn getTurn() throws BoardNotInitializedException {
		
		this.checkBoardInitialized();
		
		return this.turn;
	}
	
	public BoardModel getBoard() throws BoardNotInitializedException {
		
		this.checkBoardInitialized();
		
		BoardModel boardModelAux = new BoardModel(this.boardModel);
		
		return boardModelAux;
	}
	
	public boolean matchCards(boolean player, int r1, int c1, int r2, int c2) 
			throws BoardNotInitializedException, NotIsYourTurnException, CardNotValidException {
		
		this.checkBoardInitialized();
		
		if(player == PLAYER_ONE && !this.turn.isPlayerOne()) {
			throw new NotIsYourTurnException();
		}
		else if (player == PLAYER_TWO && !this.turn.isPlayerTwo() ){
			throw new NotIsYourTurnException();	
		}
		
		if(!this.boardModel.isCardValid(r1, c1) || !this.boardModel.isCardValid(r2, c2)) {
			throw new CardNotValidException();	
		}
		
		
		this.boardModel.setCardSelected(r1, c1);
		this.boardModel.setCardSelected(r2, c2);
				
		if(!this.boardModel.areSelectedCardsSameType()) {
			this.boardModel.setSelectedCardsHidden();
			this.turn.changeTurn();
			return false;
		}
		else {
			this.boardModel.setSelectedCardsMatched();
			return true;
		}

		
	}
	
	public boolean isSolved() throws BoardNotInitializedException {
		
		this.checkBoardInitialized();
		
		if(this.boardModel.isSolved()) {
			return true;
		}
		else return false;
	}
	
	private void checkBoardInitialized() throws BoardNotInitializedException{
		if(this.turn == null || this.boardModel == null) {
			throw new BoardNotInitializedException();
		}
	}
}
