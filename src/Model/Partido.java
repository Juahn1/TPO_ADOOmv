package Model;

import Strategy.EstrategiaEmparejamiento;

import java.util.List;

public class Partido {
	//atributos
	private Deporte deporte;
	private int cantidadJugadoresRequeridos;
	private int duracion;
	private Ubicacion ubicacion;
	private EstadoPartido estado;
	private List<Usuario> jugadoresAnotados;
	private EstrategiaEmparejamiento estrategiaEmparejamiento;
	private List<Notificador> observadores;
	private Usuario organizador;

	
	//metodos
	public void agregarJugador() {
		
	}
	
	public void eliminarJugador() {
		
	}
	
	public void cambiarEstado(EstadoPartido estado ) {
		
	}
	
	public void cancelarPartido() {
		
	}
	
	public void notificar() {
		
	}
	
	public Partido crearPartido() {
		return null;}
	
	public void editarPartido() {
		
	}
	
	public PartidoDTO partidoToDTO() {
		return null;}
	
	public void setEstrategia() {
		
	}
}
