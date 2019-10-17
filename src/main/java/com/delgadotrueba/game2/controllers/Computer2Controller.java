package com.delgadotrueba.game2.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.delgadotrueba.game2.RMI.BoardNotInitializedException;
import com.delgadotrueba.game2.RMI.BoardRMI;
import com.delgadotrueba.game2.RMI.CardNotValidException;
import com.delgadotrueba.game2.RMI.NotIsYourTurnException;
import com.delgadotrueba.game2.models.BoardModel;
import com.delgadotrueba.game2.models.CellModel;
import com.delgadotrueba.game2.notifications.ObservableBoarModel;
import com.delgadotrueba.game2.views.BoardView;

public class Computer2Controller extends Thread {

	private ObservableBoarModel model;
	private BoardView view;
	
	private int numOfMatched_P2 = 0;
	
	private BoardRMI rmi;
	private static final boolean PLAYER_ONE = false;
	private int NUMBER_OF_ROWS;
	private int NUMBER_OF_COLUMNS;
	
	public Computer2Controller(BoardRMI rmi, int rows, int cols) {	
		this.rmi = rmi;
		NUMBER_OF_ROWS = rows;
		NUMBER_OF_COLUMNS = cols;
	} 
	
	public void addModel(ObservableBoarModel m){
		this.model = m;
	}

	public void addView(BoardView v){
		this.view = v;
	}
	
	public void initModel(){
		
	}
	
	public void actionPerformed(int row, int col) {
		
  		this.model.setCardSelected(row, col);


  		if ( this.model.isPlayable() ) {
  			
  			int[][] resul = this.model.getSelectedCards();
  			
  			int r1 = resul[0][0];
  			int c1 = resul[0][1];

  			int r2 = resul[1][0];
  			int c2 = resul[1][1];

  				if (this.model.areSelectedCardsSameType()){
  	  				
  	  				this.model.setSelectedCardsMatched();
  	  				
  	  				this.view.displayNumOfMatchedPairs_P1(++numOfMatched_P2);
  	  			} else {
  	  				  				
  	  				this.model.setSelectedCardsHidden();
  	  				
  					this.view.showInfoTurnPlayer2();
  	  			}
  			
  		}
	}
	
	public void run(){  
		boolean comprobar = true;
		while(true){  
			try{
				Thread.sleep(1500);				
				

				if ((rmi.getTurn().isPlayerTwo()) & comprobar) {
				
					BoardModel olModel = this.model.getBoard();
					BoardModel newModel = rmi.getBoard();
					
					ArrayList<int[]> resul = getDifereces(olModel, newModel);
					
					if(resul != null) {
						for(int[] aux: resul) {
							int row = aux[0];
							int col = aux[1];
							actionPerformed(row, col);
							Thread.sleep(1000);
						}
					}
					
					if(this.rmi.isSolved()) {
  	  					this.view.playerLose();
  	  				}
					comprobar = false;
				}
				else if(rmi.getTurn().isPlayerOne()){
					comprobar = true;
				}
		
			}catch(InterruptedException e){
				System.out.println(e);
			} catch (BoardNotInitializedException e1) {
				System.out.println("BoardNotInitializedException");
			}
		}

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private ArrayList<int[]> getDifereces(BoardModel oldModel, BoardModel newModel){
		ArrayList<int[]> resul = null;
		ArrayList<int[]> aux = new ArrayList<int[]>();
		
		for (int row = 0; row < NUMBER_OF_ROWS; row++) {
			   for (int column = 0; column < NUMBER_OF_COLUMNS; column++) {
				   CellModel oldCell = oldModel.mBoard[row][column];
				   CellModel newCell = newModel.mBoard[row][column];
				   
				   if(!newCell.equals(oldCell)) {
					   aux.add(new int[]{row, column, newCell.getTypeInt()});
				   }
			   }
		}
		
		if(!aux.isEmpty()) {
			Collections.sort(aux, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					int[] c2 = (int[]) o2;
					int[] c1 = (int[]) o1;
					return new Integer(c2[2]).compareTo(new Integer(c1[2]));
				}
			});
			
			resul = aux;
		}
		
		return resul;
	}
}
