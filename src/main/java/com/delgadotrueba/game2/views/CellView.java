package com.delgadotrueba.game2.views;

import javax.swing.JButton;

public class CellView extends JButton{
	
	private int row;
	private int column;
	
	public CellView(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	
}
