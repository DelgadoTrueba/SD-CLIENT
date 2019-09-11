package com.delgadotrueba.game2.views;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.delgadotrueba.game2.utils.ErrorHandler;

public class Player extends JButton {
	// Card image file properties
		private static final String DEFAULT_IMAGE_FILENAME_SUFFIX = ".png";
		private static final String DEFAULT_IMAGE_FOLDER = "/images/";
	 	private static final String BOY_IMAGE_PATH = DEFAULT_IMAGE_FOLDER  + "boy" + DEFAULT_IMAGE_FILENAME_SUFFIX;
	 	private static final String GIRL_IMAGE_PATH = DEFAULT_IMAGE_FOLDER + "girl" + DEFAULT_IMAGE_FILENAME_SUFFIX;
		 
	 	private int playerPosition;
	 	
		public Player(int playerPosition) {
			super();
			this.playerPosition = playerPosition;
		}

		public void setImage() {
			String type;
			if(this.playerPosition == 1) {
				type = BOY_IMAGE_PATH;
			}
			else {
				type = GIRL_IMAGE_PATH;
			}
			File resourcesDirectory = new File("src/main/resources" + type );
			if (resourcesDirectory == null) {
				ErrorHandler.error("Player View: ", "showImage(int, int) reported error \"File not found\".", true);
			}
			this.setIcon(new ImageIcon(resourcesDirectory.getAbsolutePath()));
		}
}
