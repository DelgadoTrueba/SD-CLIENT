package com.delgadotrueba.game2;


import com.delgadotrueba.game2.models.Model;
import com.delgadotrueba.game2.controllers.Controller;
import com.delgadotrueba.game2.views.View;


public class RunMVC {

	//The order of instantiating the objects below will be important for some pairs of commands.
	//I haven't explored this in any detail, beyond that the order below works.

	private int start_value = 10;	//initialise model, which in turn initialises view

	public RunMVC() {

		//create Model and View
		Model myModel 	= new Model();
		View myView 	= new View();

		//tell Model about View. 
		myModel.addObserver(myView);
	
		//create Controller. tell it about Model and View, initialise model
		Controller myController = new Controller();
		myController.addModel(myModel);
		myController.addView(myView);
		myController.initModel(start_value);

		//tell View about Controller 
		myView.addController(myController);
		

	}
}
