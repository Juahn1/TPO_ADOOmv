package Model;

import Strategy.EstrategiaEmparejamiento;
import DTO.PartidoDTO;
import DTO.UbicacionDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import State.EstadoPartido;
import State.EstadoBuscandoJugadores;
import State.EstadoPartidoArmado;
import State.EstadoPartidoConfirmado;
import State.EstadoPartidoEnJuego;
import State.EstadoPartidoFinalizado;
import State.EstadoPartidoCancelado;

@Getter
@Setter
public class Partido {
	private int id;
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
	private LocalDateTime fechaHora;
	private int minPartidosRequeridos; // Para la estrategia de emparejamiento por historial

	public Partido(Ubicacion ubicacion, Deporte deporte, int cantidadJugadoresRequeridos,
                  int duracion, Usuario organizador, EstrategiaEmparejamiento estrategia,
                  LocalDateTime fechaHora) {
		this.ubicacion = ubicacion;
		this.deporte = deporte;
		this.cantidadJugadoresRequeridos = cantidadJugadoresRequeridos;
		this.duracion = duracion;
		this.organizador = organizador;
		this.estrategiaEmparejamiento = estrategia;
		this.estado = new EstadoBuscandoJugadores();
		this.jugadoresAnotados = new ArrayList<>();
		this.observadores = new ArrayList<>();
		this.fechaHora = fechaHora;
		this.jugadoresAnotados.add(organizador);
		this.nivelMinimo = Nivel.PRINCIPIANTE; // Por defecto
		this.nivelMaximo = Nivel.AVANZADO; // Por defecto
		this.minPartidosRequeridos = 0; // Por defecto
	}

	// métodos
	public void agregarJugador(Usuario jugador) {
		// Verificar si el jugador ya está anotado
		if (!jugadoresAnotados.contains(jugador)) {
			jugadoresAnotados.add(jugador);
			estado.agregarJugador(this); // Delegar al estado actual
		} else {
			System.out.println("El jugador ya está anotado en este partido.");
		}
	}

	public void eliminarJugador(Usuario jugador) {
		if (jugadoresAnotados.contains(jugador)) {
			jugadoresAnotados.remove(jugador);
			estado.eliminarJugador(this); // Delegar al estado actual
		} else {
			System.out.println("El jugador no está anotado en este partido.");
		}
	}

	public void cambiarEstado(EstadoPartido nuevoEstado) {
		this.estado = nuevoEstado;
		this.notificar(); // Notificar a los observadores del cambio de estado
	}

	public void cancelarPartido() {
		this.estado = new EstadoPartidoCancelado();
		this.notificar(); // Notificar la cancelación
	}

	public void notificar() {
		String mensaje = "Actualización del partido de " + deporte.getNombre() +
                    " en " + ubicacion.getDireccion() +
                    ". Estado: " + estado.getNombreEstado();

		for (Notificador n : observadores) {
			// Notificar a todos los jugadores anotados
			for (Usuario jugador : jugadoresAnotados) {
				n.notificar(jugador, mensaje);
			}
		}
	}

	public void editarPartido(Ubicacion ubicacion, int duracion, LocalDateTime fechaHora) {
		this.ubicacion = ubicacion;
		this.duracion = duracion;
		this.fechaHora = fechaHora;
	}

	// Verificar y actualizar el estado del partido según la fecha/hora actual
	public void verificarEstadoActual() {
		LocalDateTime ahora = LocalDateTime.now();

		// Si llegó la hora del partido y está confirmado, pasar a "En juego"
		if (ahora.isAfter(fechaHora) && estado instanceof EstadoPartidoConfirmado) {
			cambiarEstado(new EstadoPartidoEnJuego());
		}

		// Si terminó el tiempo de juego y está en juego, pasar a "Finalizado"
		if (estado instanceof EstadoPartidoEnJuego &&
			ahora.isAfter(fechaHora.plusMinutes(duracion))) {
			cambiarEstado(new EstadoPartidoFinalizado());
		}
	}

	public void confirmarJugadores() {
		if (estado instanceof EstadoPartidoArmado) {
			estado.confirmarJugador(this);
		}
	}

	// Método para agregar observadores (notificadores)
	public void agregarObservador(Notificador observador) {
		if (!observadores.contains(observador)) {
			observadores.add(observador);
		}
	}

	public void eliminarObservador(Notificador observador) {
		observadores.remove(observador);
	}

	public PartidoDTO toDTO() {
		PartidoDTO dto = new PartidoDTO();
		dto.setId(String.valueOf(this.id));
		dto.setNombreDeporte(this.deporte.getNombre());
		dto.setEstado(this.estado.getNombreEstado());
		dto.setUbicacion(this.ubicacion != null ? this.ubicacion.toDTO() : null);
		dto.setFechaHora(this.fechaHora.toString());
		dto.setCantidadJugadoresAnotados(this.jugadoresAnotados.size());
		dto.setCantidadJugadoresRequeridos(this.cantidadJugadoresRequeridos);
		dto.setDuracion(this.duracion);
		return dto;
	}
}
