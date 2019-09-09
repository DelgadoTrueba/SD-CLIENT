package com.delgadotrueba.game2.controllers;
//Controller.java
//(C) Joseph Mack 2011, jmack (at) wm7d (dot) net, released under GPL v3 (or any later version)

import com.delgadotrueba.game2.views.View;
import com.delgadotrueba.game2.models.Model;

public class Controller implements java.awt.event.ActionListener {

	Model model;
	View view;

	public Controller() {	
		// Must be empty
	} 

	//invoked when a button is pressed
	public void actionPerformed(java.awt.event.ActionEvent e){
		model.incrementValue();
	}

	public void addModel(Model m){
		this.model = m;
	}

	public void addView(View v){
		this.view = v;
	}

	public void initModel(int x){
		model.setValue(x);
	}

}
