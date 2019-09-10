package com.delgadotrueba.game2.models;

import com.delgadotrueba.game2.errors.ErrorHandler;

public class CellModel {

	////////////////////////////////////////////////////////////////////////////
	// Constant
	////////////////////////////////////////////////////////////////////////////
	
	 
	// Cell types -> Config File
	private static final int MIN_TYPE_RANGE = 0;
	private static final int EMPTY_CELL_TYPE = 25;
	private static final int MAX_TYPE_RANGE = 26;
	 
	////////////////////////////////////////////////////////////////////////////
	// Instance variables
	////////////////////////////////////////////////////////////////////////////
	private boolean mIsSelected = false;
	private boolean mIsMatched = false;
	private int mType = EMPTY_CELL_TYPE;
	 
	public CellModel(int aType) {
		this.mType = aType;
	}
	
	////////////////////////////////////////////////////////////////////////////
	// Public Interface
	////////////////////////////////////////////////////////////////////////////
	
	public int getType() {
		return mType;
	}
	
	public void setType(int aType) {
		if (aType > MAX_TYPE_RANGE || aType < MIN_TYPE_RANGE){
			ErrorHandler.error("CELL: ", "setType(int) reported \"Invalid type code\"", true);
		}
		mType = aType;
	}
		
	public boolean sameType(CellModel other) {
		if (other == null) {
			ErrorHandler.error("CELL: ", "sameType(Cell) received null", false);
			return false;
		}
		
		if (this.getType() == other.getType()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmpty() {
		if (this.mType != EMPTY_CELL_TYPE) {
			return false;
		}
		return true;
	}
	
	public void setSelected(boolean selected) {
		mIsSelected = selected;
	}
	
	public void setMatched(boolean matched) {
		mIsMatched = matched;
	}
	
	public boolean isSelected() {
		if (mIsSelected == true) {
			return true;
		}
		return false;
	}
	
	public boolean isMatched() {
		if (mIsMatched == true) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
