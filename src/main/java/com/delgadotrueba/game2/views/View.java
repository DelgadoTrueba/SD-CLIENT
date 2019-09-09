package com.delgadotrueba.game2.views;

import java.awt.Button;
import java.awt.Panel;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.Label;
import java.awt.event.WindowEvent;	//for CloseListener()
import java.awt.event.WindowAdapter;	//for CloseListener()
import java.util.Observable;		//for update();

import com.delgadotrueba.game2.models.Model;
import com.delgadotrueba.game2.notifications.ActionsModel;
import com.delgadotrueba.game2.notifications.ModelNotification;

import java.awt.event.ActionListener;	//for addController()


public class View implements java.util.Observer {

	private TextField myTextField;
	private Button button; 
	
	public View() {
		
		//frame in constructor and not an attribute as doesn't need to be visible to whole class
		Frame frame 		= new Frame("simple MVC");
		frame.add("North", new Label("counter"));

		myTextField 		= new TextField();
		frame.add("Center", myTextField);

		//panel in constructor and not an attribute as doesn't need to be visible to whole class
		Panel panel 		= new Panel();
		button	 		= new Button("PressMe");
		panel.add(button);
		frame.add("South", panel);		

		frame.addWindowListener(new CloseListener());	
		frame.setSize(200,100);
		frame.setLocation(100,100);
		frame.setVisible(true);

	}

	// Called from the Model
	public void update(Observable obs, Object obj) {

    	ModelNotification notification = ((ModelNotification)obj);
    	
    	if(obs instanceof Model && ActionsModel.incrementValue.equals(notification.action)) {
    		this.setValue(notification.model.getValue());
    	}

	} 

	public void addController(ActionListener controller){
		button.addActionListener(controller);	//need instance of controller before can add it as a listener 
	}

	public void setValue(int v){
    		myTextField.setText("" + v);
	}
    	
	public static class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			e.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}
