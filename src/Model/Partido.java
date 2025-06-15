package Model;

import Strategy.EstrategiaEmparejamiento;
import DTO.PartidoDTO;
import java.util.ArrayList;
import java.util.List;
import State.EstadoPartido;
import State.EstadoBuscandoJugadores;

public class Partido {
	// atributos
	private Deporte deporte;
	private int cantidadJugadoresRequeridos;
	private int duracion;
	private Nivel nivelMaximo;
	private Nivel nivelMinimo;
	private Ubicacion ubicacion;
	private EstadoPartido estado;
	private List<Usuario> jugadoresAnotados;
	private EstrategiaEmparejamiento estrategiaEmparejamiento;
	private List<Notificador> observadores;
	private Usuario organizador;

	public Partido(Ubicacion ubicacion, Deporte deporte, int cantidadJugadoresRequeridos, int duracion, Usuario organizador, EstrategiaEmparejamiento estrategia) {
		this.ubicacion = ubicacion;
		this.deporte = deporte;
		this.cantidadJugadoresRequeridos = cantidadJugadoresRequeridos;
		this.duracion = duracion;
		this.organizador = organizador;
		this.estrategiaEmparejamiento = estrategia;
		this.estado = new EstadoBuscandoJugadores(); // ✅ instancia válida
		this.jugadoresAnotados = new ArrayList<>();
		this.observadores = new ArrayList<>();
	}

	// métodos
	public void agregarJugador() {
		// Implementar lógica si es necesario
	}

	public void eliminarJugador() {
		// Implementar lógica si es necesario
	}

	public void cambiarEstado(EstadoPartido nuevoEstado) {
		this.estado = nuevoEstado;
	}

	public void cancelarPartido() {
		// Implementar lógica si es necesario
	}

	public void notificar() {
		for (Notificador n : observadores) {
			n.notificar(organizador, "Actualización del partido en " + ubicacion);
		}
	}

	public Partido crearPartido() {
		return null;
	}

	public void editarPartido() {
		// Implementar si es necesario
	}

	public void setEstrategia(EstrategiaEmparejamiento estrategia) {
		this.estrategiaEmparejamiento = estrategia;
	}

	public void setNivelMinimo(Nivel nivelMinimo) {
		this.nivelMinimo = nivelMinimo;
	}

	public void setNivelMaximo(Nivel nivelMaximo) {
		this.nivelMaximo = nivelMaximo;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public EstadoPartido getEstado() {
		return estado;
	}

	public PartidoDTO toDTO() {
		PartidoDTO dto = new PartidoDTO();
		dto.setNombreDeporte(this.deporte.getNombre());
		dto.setEstado(this.estado.getNombreEstado());
		dto.setUbicacion(this.ubicacion.toDTO());
		return dto;
	}
}

