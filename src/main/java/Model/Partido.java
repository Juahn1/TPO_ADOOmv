package Model;

import Strategy.EstrategiaEmparejamiento;
import DTO.PartidoDTO;
import DTO.UbicacionDTO;
import jakarta.persistence.*;
import lombok.*;

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


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Partido {
	@Id
	@GeneratedValue
	private float id;
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
	private int minPartidosRequeridos;


	public void agregarJugador(Usuario jugador) {
		if (!jugadoresAnotados.contains(jugador)) {
			jugadoresAnotados.add(jugador);
			estado.agregarJugador(this);
		} else {
			System.out.println("El jugador ya está anotado en este partido.");
		}
	}

	public void eliminarJugador(Usuario jugador) {
		if (jugadoresAnotados.contains(jugador)) {
			jugadoresAnotados.remove(jugador);
			estado.eliminarJugador(this);
		} else {
			System.out.println("El jugador no está anotado en este partido.");
		}
	}

	public void cambiarEstado(EstadoPartido nuevoEstado) {
		this.estado = nuevoEstado;
		this.notificar();
	}

	public void cancelarPartido() {
		this.estado = new EstadoPartidoCancelado();
		this.notificar();
	}

	public void notificar() {
		String mensaje = "Actualización del partido de " + deporte.getNombre() +
                    " en " + ubicacion.getDireccion() +
                    ". Estado: " + estado.getNombreEstado();

		for (Notificador n : observadores) {
			for (Usuario jugador : jugadoresAnotados) {
				n.notificar(jugador, mensaje);
			}
		}
	}

	public

	public void editarPartido(Ubicacion ubicacion, int duracion, LocalDateTime fechaHora) {
		this.ubicacion = ubicacion;
		this.duracion = duracion;
		this.fechaHora = fechaHora;
	}

	public void verificarEstadoActual() {
		LocalDateTime ahora = LocalDateTime.now();

		// Si llegó la hora del partido y está confirmado, pasará al estado "En juego"
		if (ahora.isAfter(fechaHora) && estado instanceof EstadoPartidoConfirmado) {
			cambiarEstado(new EstadoPartidoEnJuego());
		}

		// Si terminó el partido, pasará al estado "Finalizado"
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

	public void agregarObservador(Notificador observador) {
		if (!observadores.contains(observador)) {
			observadores.add(observador);
		}
	}

	public void eliminarObservador(Notificador observador) {
		observadores.remove(observador);
	}

	public PartidoDTO toDTO() {
		return PartidoDTO.builder()
				.id(String.valueOf(this.id))
				.nombreDeporte(this.deporte.getNombre())
				.estado(this.estado != null ? this.estado.getNombreEstado() : "Necesitamos jugadores")
				.ubicacion(this.ubicacion != null ? this.ubicacion.toDTO() : null)
				.fechaHora(this.fechaHora.toString())
				.cantidadJugadoresAnotados(this.jugadoresAnotados != null ? this.jugadoresAnotados.size() : 1)
				.cantidadJugadoresRequeridos(this.cantidadJugadoresRequeridos)
				.duracion(this.duracion)
				.build();
	}

}
