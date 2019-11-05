package com.delgadotrueba.game2.interfazRMI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_EmparejarCartas;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_EstaResuelto;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_IniciarPartida;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_ObtenerCartasEmparejadas;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_ObtenerPuntosJugador;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Input_TiposCartas;
import com.delgadotrueba.game2.interfazRMI.dto.DTO_Output;

public class IONetworkClient extends IONetwork{
	
	public IONetworkClient(Socket socketCliente) throws IOException {
		super(socketCliente);
	}
	
	public void cerrarSocket() throws IOException {
		super.cerrarSocket();
	}
	
	/*********************************************************************************/
	/** ENVIO DE MENSAJES
	/*********************************************************************************/
	
	public void enviarMensaje(DTO_Output dtoOutput) throws IOException {
		dtoOutput.enviarDatosAlServidor(dataOutput);
	}

	
	/*********************************************************************************/
	/** RECEPCIÓN DE RESPUESTAS => LO HACEN LOS DTOS_INPUT
	/*********************************************************************************/
	
	
	/*********************************************************************************/
	/** GETTERS AND SETTERS
	/*********************************************************************************/

	public DataInputStream getDataIn() {
		return super.dataInput;
	}

	public DataOutputStream getDataOut() {
		return super.dataOutput;
	}

}
