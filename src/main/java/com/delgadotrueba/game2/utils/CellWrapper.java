package com.delgadotrueba.game2.utils;

import com.delgadotrueba.game2.models.CellModel;
import com.delgadotrueba.game2.views.CellView;

public class CellWrapper {
	
	private CellModel cellModel;
	private CellView cellView;
	
	public CellWrapper(CellModel cellModel, CellView cellView) {
		this.cellModel = cellModel;
		this.cellView = cellView;
	}

	public CellModel getCellModel() {
		return cellModel;
	}

	public int getRow() {
		return cellView.getRow();
	}
	
	public int getColumn() {
		return cellView.getColumn();
	}
	
}
