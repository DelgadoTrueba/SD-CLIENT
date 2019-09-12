package com.delgadotrueba.game2.utils;

public class Turn {

	private boolean playerOne;
	private boolean playerTwo;
	
	public Turn(boolean playerOne, boolean playerTwo) {
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	public boolean isPlayerOne() {
		return playerOne;
	}

	public boolean isPlayerTwo() {
		return playerTwo;
	}
	
	public void changeTurn() {
		this.playerOne = !playerOne;
		this.playerTwo = !playerTwo;
	}
	
}
