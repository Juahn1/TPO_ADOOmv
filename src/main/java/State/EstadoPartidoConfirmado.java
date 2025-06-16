package State;

import Model.Partido;

import java.time.LocalDateTime;

public class EstadoPartidoConfirmado implements EstadoPartido {

	@Override
	public void agregarJugador(Partido partido) {
		System.out.println("No se pueden agregar más jugadores al partido confirmado.");
	}

	@Override
	public void eliminarJugador(Partido partido) {
		System.out.println("Jugador eliminado del partido confirmado.");
		partido.cambiarEstado(new EstadoPartidoArmado());
		System.out.println("El partido ha vuelto al estado: Armado");
	}

	@Override
	public void confirmarJugador(Partido partido) {
		System.out.println("Todos los jugadores ya están confirmados.");
	}

	@Override
	public void cancelarPartido() {
		System.out.println("Partido confirmado ha sido cancelado.");
	}

	public void verificarInicio(Partido partido, LocalDateTime ahora) {
		if (ahora.isAfter(partido.getFechaHora())) {
			partido.cambiarEstado(new EstadoPartidoEnJuego());
			System.out.println("Inicia el partidoooooooo El partido ha pasado a estado: En Juego!! ");
		}
	}
}