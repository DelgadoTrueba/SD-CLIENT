package com.delgadotrueba.clienteJuego.juego.utils;

public class ErrorHandler {
	
	public static void error(String tag, String message, boolean crash ){
		System.err.println( tag + message );
		if (crash) System.exit(-1);
	}
}
