package com.delgadotrueba.game2.views;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.delgadotrueba.game2.utils.ErrorHandler;

public class CellView extends JButton{
	
	// Card image file properties
	private static final String DEFAULT_IMAGE_FILENAME_SUFFIX = ".jpg";
	private static final String DEFAULT_IMAGE_FILENAME_PREFIX = "img-";
	private static final String DEFAULT_IMAGE_FOLDER = "/images/";
 	private static final String HIDDEN_IMAGE_PATH = DEFAULT_IMAGE_FOLDER + DEFAULT_IMAGE_FILENAME_PREFIX + "26" + DEFAULT_IMAGE_FILENAME_SUFFIX;
 	private static final String EMPTY_IMAGE_PATH = DEFAULT_IMAGE_FOLDER + DEFAULT_IMAGE_FILENAME_PREFIX + "25" + DEFAULT_IMAGE_FILENAME_SUFFIX;
	 
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

	public int getColumn() {
		return column;
	}

	public void setImage(String num) {
		File resourcesDirectory = new File("src/main/resources" + DEFAULT_IMAGE_FOLDER + DEFAULT_IMAGE_FILENAME_PREFIX + num + DEFAULT_IMAGE_FILENAME_SUFFIX);
		if (resourcesDirectory == null) {
			ErrorHandler.error("Board View: ", "showImage(int, int) reported error \"File not found\".", true);
		}
		this.setIcon(new ImageIcon(resourcesDirectory.getAbsolutePath()));
	}
	
	public void setEmptyImage() {
		File resourcesDirectory = new File("src/main/resources" + EMPTY_IMAGE_PATH);
		this.setIcon(new ImageIcon(resourcesDirectory.getAbsolutePath()));
	}
	
	public void setHiddenImage() {
		File resourcesDirectory = new File("src/main/resources" + HIDDEN_IMAGE_PATH);
		this.setIcon(new ImageIcon(resourcesDirectory.getAbsolutePath()));
	}
	
}
