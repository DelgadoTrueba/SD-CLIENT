package com.delgadotrueba.game2;

import com.delgadotrueba.game2.RMI.BoardAlreadyCreatedException;
import com.delgadotrueba.game2.RMI.BoardNotInitializedException;

public class Main{

	public static void main(String[] args) throws BoardAlreadyCreatedException, BoardNotInitializedException{

		//RunMVC mainRunMVC = new RunMVC();
		
		RunBoardMVC mainRunBoardMVC = new RunBoardMVC();
		
	} 

}
