package com.delgadotrueba.game2.views;

import java.awt.Color;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.delgadotrueba.game2.utils.ErrorHandler;

public class IconPlayer extends JButton {

	private static final long serialVersionUID = 1L;

	// Card image file properties
	private static final String DEFAULT_IMAGE_FILENAME_SUFFIX = ".png";
	private static final String DEFAULT_IMAGE_FOLDER = "/images/";
		 
	 	private String icon;
	 	
		public IconPlayer(String iconName) {
			super();
			this.icon = iconName;
			setImage();
			if(iconName.equals("boy")) {
				setBackground(Color.CYAN);
			}
			else{
				setBackground(Color.YELLOW);
			}
		}

		private void setImage() {
			File resourcesDirectory = new File("src/main/resources" + DEFAULT_IMAGE_FOLDER  + icon + DEFAULT_IMAGE_FILENAME_SUFFIX );
			if (resourcesDirectory == null) {
				ErrorHandler.error("Player View: ", "showImage(int, int) reported error \"File not found\".", true);
			}
			this.setIcon(new ImageIcon(resourcesDirectory.getAbsolutePath()));
		}
}
