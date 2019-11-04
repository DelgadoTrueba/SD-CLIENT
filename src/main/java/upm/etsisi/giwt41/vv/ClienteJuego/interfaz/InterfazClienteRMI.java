package upm.etsisi.giwt41.vv.ClienteJuego.interfaz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaEmparejarCartas;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaEstaResuelto;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaIniciarPartida;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaObtenerCartasEmparejadas;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaObtenerPuntosJugador;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuetaTiposCartas;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.utils.IONetworkClient;

public class InterfazClienteRMI implements ClienteRMI {

	private IONetworkClient cliente;
	
	public void newGame() throws RMIClientException {

		try {
			cliente = this.crearSocket();
			cliente.enviarMensaje_IniciarPartida();
			RespuestaIniciarPartida respuesta 
				= cliente.recibirRespuesta_IniciarPartida();
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

	public byte[] obtenerTiposCartas() throws RMIClientException {
		try {
			cliente = this.crearSocket();
			cliente.enviarMensaje_ObtenerTiposCartas();
			RespuetaTiposCartas respuesta 
				= cliente.recibirRespuesta_ObtenerTiposCartas();
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
			cliente.enviarMensaje_ObtenerCartasEmparejadas();
			RespuestaObtenerCartasEmparejadas respuesta 
				= cliente.recibirRespuesta_ObtenerCartasEmparejadas();
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
			cliente.enviarMensaje_ObtenerPuntosJ1();
			RespuestaObtenerPuntosJugador respuesta 
				= cliente.recibirRespuesta_ObtenerPuntosJ1();
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
			cliente.enviarMensaje_ObtenerPuntosJ2();
			RespuestaObtenerPuntosJugador respuesta 
				= cliente.recibirRespuesta_ObtenerPuntosJ2();
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
			cliente.enviarMensaje_EmparejarCartas(r1, c1, r2, c2);
			RespuestaEmparejarCartas respuesta 
				= cliente.recibirRespuesta_EmparejarCartas();
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
			cliente.enviarMensaje_EstaResuelto();
			RespuestaEstaResuelto respuesta 
				= cliente.recibirRespuesta_EstaResuelto();
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
			cliente.enviarMensaje_CartasSeleccionadasJ2();
			RespuestaObtenerCartasEmparejadas respuesta 
				= cliente.recibirRespuesta_CartasSeleccionadasJ2();
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
