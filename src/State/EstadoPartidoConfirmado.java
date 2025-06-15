package Model;

import java.time.LocalDateTime;

public class EstadoPartidoConfirmado extends EstadoPartido {

	@Override
	public void agregarJugador(Partido contexto) {
		System.out.println("No se pueden agregar más jugadores al partido confirmado.");
	}

	@Override
	public void eliminarJugador(Partido contexto) {
		System.out.println("Jugador eliminado del partido confirmado.");
		contexto.cambiarEstado(new EstadoPartidoArmado());
		System.out.println("El partido ha vuelto al estado: Armado");
	}

	@Override
	public void confirmarJugador(Partido contexto) {
		System.out.println("Todos los jugadores ya están confirmados.");
	}

	@Override
	public void cancelarPartido() {
		System.out.println("Partido confirmado ha sido cancelado.");
	}

	public void verificarInicio(Partido contexto, LocalDateTime ahora) {
		if (ahora.isAfter(contexto.getFechaHora())) {
			contexto.cambiarEstado(new EstadoPartidoEnJuego());
			System.out.println("Inicia el partidoooooooo El partido ha pasado a estado: En Juego!! ");
		}
	}
}