package com.delgadotrueba.game2.notifications;

import java.util.ArrayList;
import java.util.Observable;

import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.notifications.ActionsBoardModel;
import com.delgadotrueba.game2.notifications.BoardModelNotification;

public class ObservableBoarModel extends Observable {

	private BoardModel boardModel;

	public ObservableBoarModel(BoardModel boardModel) {
		this.boardModel = boardModel;
	}
	
	/**THESE METHODS NOTIFICATE TO THE VIEW**/
	public void setCardSelectedJ1(int row, int col){
		this.boardModel.setCardSelected(row, col);
		
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setSelectedCardJ1, this.boardModel, row, col));
	}
	
	public void setCardSelectedJ2(int row, int col){
		this.boardModel.setCardSelected(row, col);
		
		setChanged();
		notifyObservers(new BoardModelNotification(ActionsBoardModel.setSelectedCardJ2, this.boardModel, row, col));
	}
	
	public void setSelectedCardsHidden() {

		int[][] resul = this.boardModel.setSelectedCardsHidden();
		
		if(resul[0][0] != -1 && resul[0][1] != -1 && resul[1][0] != -1 && resul[1][1] != -1) {
			
			int row = resul[0][0];
			int column = resul[0][1];
			
			setChanged();
			notifyObservers(new BoardModelNotification(ActionsBoardModel.setHiddenCard, this.boardModel, row, column));
			
			row = resul[1][0];
			column = resul[1][1];
			
			setChanged();
			notifyObservers(new BoardModelNotification(ActionsBoardModel.setHiddenCard, this.boardModel, row, column));
		}
	}
		
	public void setSelectedCardsMatched() {
		
		int[][] resul = this.boardModel.setSelectedCardsMatched();
		
		if(resul[0][0] != -1 && resul[0][1] != -1 && resul[1][0] != -1 && resul[1][1] != -1) {
			int row = resul[0][0];
			int column = resul[0][1];
			
			setChanged();
			notifyObservers(new BoardModelNotification(ActionsBoardModel.setMatchedCard, this.boardModel, row, column));
			
			row = resul[1][0];
			column = resul[1][1];
			
			setChanged();
			notifyObservers(new BoardModelNotification(ActionsBoardModel.setMatchedCard, this.boardModel, row, column));
		}
		
	}
	/****/
	
	public boolean isCardValid(int row, int col) {
		return this.boardModel.isCardValid(row, col);
	}
	
	public boolean isPlayable() {
		return this.boardModel.isPlayable();
	}
	
	public boolean areSelectedCardsSameType() {
		return this.boardModel.areSelectedCardsSameType();
	}
	
	public boolean isSolved() {	
		return this.boardModel.isSolved();
	}
	
	public void initializeNewBoard() {
		this.boardModel.initializeNewBoard();
	}
	
	public int[][] getSelectedCards(){
		return this.boardModel.getSelectedCards();
	}
		
	public int getMatchedCard() {
		return this.boardModel.getMatchedCard();
	}
	
}
