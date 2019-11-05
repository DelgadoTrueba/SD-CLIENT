package com.delgadotrueba.game2.interfazRMI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_EmparejarCartas;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_EstaResuelto;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_IniciarPartida;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_ObtenerCartasEmparejadas;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_ObtenerPuntosJugador;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_TiposCartas;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Output;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Output_MacthCard;
import com.delgadotrueba.game2.interfazRMI.exceptions.RMIClientException;

public class InterfazClienteRMI implements ClienteRMI {

	private static byte CODOP_1 = 1;
	private static byte CODOP_2 = 2;
	private static byte CODOP_3 = 3;
	private static byte CODOP_4 = 4;
	private static byte CODOP_5 = 5;
	private static byte CODOP_6 = 6;
	private static byte CODOP_7 = 7;
	private static byte CODOP_8 = 8;

	
	private IONetworkClient cliente;
	
	public void newGame() throws RMIClientException {

		try {
			cliente = this.crearSocket();
			
			DTO_Output dtoOutput = new DTO_Output(CODOP_1);
			cliente.enviarMensaje(dtoOutput);
			
			DTO_Input_IniciarPartida respuesta = new DTO_Input_IniciarPartida(cliente.getDataIn());
			cliente.cerrarSocket();
			
			if(respuesta.getErr()) {
				throw respuesta.getException();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR EN LA CONEXION CON EL SERVIDOR");
			throw new RMIClientException();
		}
	}

	public byte[][] obtenerTiposCartas() throws RMIClientException {
		try {
			cliente = this.crearSocket();
			
			DTO_Output dtoOutput = new DTO_Output(CODOP_2);
			cliente.enviarMensaje(dtoOutput);
			
			DTO_Input_TiposCartas respuesta = new DTO_Input_TiposCartas(cliente.getDataIn());
			cliente.cerrarSocket();
			
			if(respuesta.getErr()) {
				throw respuesta.getException();
			}
			
			return respuesta.getTipos();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR EN LA CONEXION CON EL SERVIDOR");
			throw new RMIClientException();
		}
	}

	public int obtenerCartasEmparejadas() throws RMIClientException {
		try {
			cliente = this.crearSocket();
			
			DTO_Output dtoOutput = new DTO_Output(CODOP_3);
			cliente.enviarMensaje(dtoOutput);
			
			DTO_Input_ObtenerCartasEmparejadas respuesta 
				= new DTO_Input_ObtenerCartasEmparejadas(cliente.getDataIn());
			cliente.cerrarSocket();
			
			if(respuesta.getErr()) {
				throw respuesta.getException();
			}
			
			return respuesta.getParejas();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR EN LA CONEXION CON EL SERVIDOR");
			throw new RMIClientException();
		}
	}

	public byte obtenerPuntosJ1() throws RMIClientException {
		try {
			cliente = this.crearSocket();
			
			DTO_Output dtoOutput = new DTO_Output(CODOP_4);
			cliente.enviarMensaje(dtoOutput);
			
			DTO_Input_ObtenerPuntosJugador respuesta 
				= new DTO_Input_ObtenerPuntosJugador(cliente.getDataIn());
			cliente.cerrarSocket();
			
			if(respuesta.getErr()) {
				throw respuesta.getException();
			}
			
			return respuesta.getPuntos();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR EN LA CONEXION CON EL SERVIDOR");
			throw new RMIClientException();
		}
	}

	public byte obtenerPuntosJ2() throws RMIClientException {
		try {
			cliente = this.crearSocket();
			
			DTO_Output dtoOutput = new DTO_Output(CODOP_5);
			cliente.enviarMensaje(dtoOutput);
			
			DTO_Input_ObtenerPuntosJugador respuesta 
				= new DTO_Input_ObtenerPuntosJugador(cliente.getDataIn());
			
			cliente.cerrarSocket();
			
			if(respuesta.getErr()) {
				throw respuesta.getException();
			}
			
			return respuesta.getPuntos();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR EN LA CONEXION CON EL SERVIDOR");
			throw new RMIClientException();
		}
	}

	public boolean matchCardsP1(byte r1, byte c1, byte r2, byte c2) throws RMIClientException {
		try {
			cliente = this.crearSocket();
			
			DTO_Output_MacthCard dtoOutput = new DTO_Output_MacthCard(CODOP_6, r1, c1, r2, c2);
			cliente.enviarMensaje(dtoOutput);
			
			
			DTO_Input_EmparejarCartas respuesta 
				= new DTO_Input_EmparejarCartas(cliente.getDataIn());
			
			cliente.cerrarSocket();
			
			if(respuesta.getErr()) {
				throw respuesta.getException();
			}
			
			return respuesta.getResultado();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR EN LA CONEXION CON EL SERVIDOR");
			throw new RMIClientException();
		}
	}

	public boolean isSolved() throws RMIClientException {
		try {
			cliente = this.crearSocket();
			
			DTO_Output dtoOutput = new DTO_Output(CODOP_7);
			cliente.enviarMensaje(dtoOutput);
						
			DTO_Input_EstaResuelto respuesta 
				= new DTO_Input_EstaResuelto(cliente.getDataIn());
			
			cliente.cerrarSocket();
			
			if(respuesta.getErr()) {
				throw respuesta.getException();
			}
			
			return respuesta.getResuelto();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR EN LA CONEXION CON EL SERVIDOR");
			throw new RMIClientException();
		}
	}

	public int obtenerCartasSeleccionadasJ2() throws RMIClientException {
		try {
			cliente = this.crearSocket();
			
			DTO_Output dtoOutput = new DTO_Output(CODOP_8);
			cliente.enviarMensaje(dtoOutput);
			
			DTO_Input_ObtenerCartasEmparejadas respuesta 
				= new DTO_Input_ObtenerCartasEmparejadas(cliente.getDataIn());
			
			cliente.cerrarSocket();
			
			if(respuesta.getErr()) {
				throw respuesta.getException();
			}
			
			return respuesta.getParejas();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR EN LA CONEXION CON EL SERVIDOR");
			throw new RMIClientException();
		}
	}
	
	private IONetworkClient crearSocket() throws IOException, UnknownHostException{
		Socket socketCliente = null;
		
		System.out.println("Conectando con servidor ...");
		socketCliente = new Socket("localhost", 10000);
		System.out.println("Conexión creada");

		return new IONetworkClient(socketCliente);
	}

}
