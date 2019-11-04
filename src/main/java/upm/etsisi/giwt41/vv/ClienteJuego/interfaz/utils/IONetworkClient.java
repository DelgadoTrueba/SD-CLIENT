package upm.etsisi.giwt41.vv.ClienteJuego.interfaz.utils;

import java.io.IOException;
import java.net.Socket;

import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaEmparejarCartas;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaEstaResuelto;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaIniciarPartida;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaObtenerCartasEmparejadas;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuestaObtenerPuntosJugador;
import upm.etsisi.giwt41.vv.ClienteJuego.interfaz.dto.RespuetaTiposCartas;

public class IONetworkClient extends IONetwork{
	
	private static byte CODOP_1 = 1;
	private static byte CODOP_2 = 2;
	private static byte CODOP_3 = 3;
	private static byte CODOP_4 = 4;
	private static byte CODOP_5 = 5;
	private static byte CODOP_6 = 6;
	private static byte CODOP_7 = 7;
	private static byte CODOP_8 = 8;


	public IONetworkClient(Socket socketCliente) throws IOException {
		super(socketCliente);
	}
	
	public void cerrarSocket() throws IOException {
		super.cerrarSocket();
	}
	
	/*********************************************************************************/
	/** ENVIO DE MENSAJES
	/*********************************************************************************/
	
	public void enviarMensaje_IniciarPartida() throws IOException {
		super.dataOutput.write(CODOP_1);
	}
	
	public void enviarMensaje_ObtenerTiposCartas() throws IOException {
		super.dataOutput.write(CODOP_2);
	}
	
	public void enviarMensaje_ObtenerCartasEmparejadas() throws IOException {
		super.dataOutput.write(CODOP_3);
	}
	
	public void enviarMensaje_ObtenerPuntosJ1() throws IOException {
		super.dataOutput.write(CODOP_4);
	}
	
	public void enviarMensaje_ObtenerPuntosJ2() throws IOException {
		super.dataOutput.write(CODOP_5);
	}
	
	public void enviarMensaje_EmparejarCartas(byte r1, byte c1, byte r2, byte c2) throws IOException {
		super.dataOutput.write(CODOP_6);
		super.dataOutput.write(r1);
		super.dataOutput.write(c1);
		super.dataOutput.write(r2);
		super.dataOutput.write(c2);
	}
	
	public void enviarMensaje_EstaResuelto() throws IOException {
		super.dataOutput.write(CODOP_7);
	}
	
	public void enviarMensaje_CartasSeleccionadasJ2() throws IOException {
		super.dataOutput.write(CODOP_8);
	}
	
	/*********************************************************************************/
	/** RECEPCIÓN DE RESPUESTAS
	/*********************************************************************************/
	
	public RespuestaIniciarPartida recibirRespuesta_IniciarPartida(){
		try {
			boolean err = this.recibirRespuesta_Error();
			if(err) {
				return new RespuestaIniciarPartida(err, null);
			}
			else {
				return new RespuestaIniciarPartida(err, null);
			}
		} catch (IOException e) {
			return null;
		}
	}
	
	public RespuetaTiposCartas recibirRespuesta_ObtenerTiposCartas(){
		try {
			boolean err = this.recibirRespuesta_Error();
			if(err) {
				return new RespuetaTiposCartas(err, null, null);
			}
			else {
				byte tipo;
				byte[] tipos = new byte [16];
				for(int i = 0; i<16; i++) {
					tipo = super.dataInput.readByte();
					tipos[i]=tipo;
				}
				
				return new RespuetaTiposCartas(err, tipos, null);
			}
		} catch (IOException e) {
			return null;
		}
	}
	
	public RespuestaObtenerCartasEmparejadas recibirRespuesta_ObtenerCartasEmparejadas(){
		try {
			boolean err = this.recibirRespuesta_Error();
			if(err) {
				int parejas_null = 0;
				return new RespuestaObtenerCartasEmparejadas(err, parejas_null, null);
			}
			else {
				int parejas = super.dataInput.readInt();
				return new RespuestaObtenerCartasEmparejadas(err, parejas,  null);
			}
		} catch (IOException e) {
			return null;
		}
	}
	
	public RespuestaObtenerPuntosJugador recibirRespuesta_ObtenerPuntosJ1(){
		try {
			boolean err = this.recibirRespuesta_Error();
			return recibirRespuesta_ObtenerPuntos(err);
		} catch (IOException e) {
			return null;
		}
	}
	
	public RespuestaObtenerPuntosJugador recibirRespuesta_ObtenerPuntosJ2(){
		try {
			boolean err = this.recibirRespuesta_Error();
			return recibirRespuesta_ObtenerPuntos(err);
		} catch (IOException e) {
			return null;
		}
	}
	
	public RespuestaEmparejarCartas recibirRespuesta_EmparejarCartas(){
		try {
			boolean err = this.recibirRespuesta_Error();
			if(err) {
				boolean resul_null = false;
				return new RespuestaEmparejarCartas(err, resul_null, null);
			}
			else {
				boolean resul = super.dataInput.readBoolean();
				return new RespuestaEmparejarCartas(err, resul, null);
			}
		} catch (IOException e) {
			return null;
		}
	}
	
	
	public RespuestaEstaResuelto recibirRespuesta_EstaResuelto(){
		try {
			boolean err = this.recibirRespuesta_Error();
			if(err) {
				boolean resuelto_null = false;
				return new RespuestaEstaResuelto(err, resuelto_null, null);
			}
			else {
				boolean resuelto = super.dataInput.readBoolean();
				return new RespuestaEstaResuelto(err, resuelto,  null);
			}
		} catch (IOException e) {
			return null;
		}
	}

	public RespuestaObtenerCartasEmparejadas recibirRespuesta_CartasSeleccionadasJ2(){
		try {
			boolean err = this.recibirRespuesta_Error();
			if(err) {
				int seleccionadas_null = 0;
				return new RespuestaObtenerCartasEmparejadas(err, seleccionadas_null, null);
			}
			else {
				int seleccionadas = super.dataInput.readInt();
				return new RespuestaObtenerCartasEmparejadas(err, seleccionadas,  null);
			}
		} catch (IOException e) {
			return null;
		}
	}
	
	
	/*********************************************************************************/
	/** METODOS PRIVADOS
	/*********************************************************************************/
	private boolean recibirRespuesta_Error() throws IOException {
		return super.dataInput.readBoolean();
	}

	private RespuestaObtenerPuntosJugador recibirRespuesta_ObtenerPuntos(boolean err) throws IOException {
		if(err) {
			byte puntos_null = 0;
			return new RespuestaObtenerPuntosJugador(err, puntos_null, null);
		}
		else {
			byte puntos = super.dataInput.readByte();
			return new RespuestaObtenerPuntosJugador(err, puntos,  null);
		}
	}

}
