package Model;

public class PartidoDTO {
	private Deporte deporte;
	private int cantJugadores;
	private EstadoPartido estado;
	private Ubicacion ubicacion;
	private int duracion;
	private int cantidadJugadoresRequeridos;
	
	public PartidoDTO(String id, Deporte deporte, int cantJugadores, int duracion, Ubicacion ubicacion) {
		this.id = id;
		this.deporte = deporte;
		this.cantJugadores = cantJugadores;
		this.duracion = duracion;
		this.ubicacion = ubicacion;
		
	}
	
}
