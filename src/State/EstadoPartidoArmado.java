package Model;

public class EstadoPartidoArmado extends EstadoPartido {

	@Override
	public void agregarJugador(Partido contexto) {
		System.out.println("No se puede! El partido ya tiene a todos sus jugadores anotados y está armado!");
	}

	@Override
	public void eliminarJugador(Partido contexto) {
		System.out.println("Jugador eliminado del partido armado.");
		if (contexto.getJugadoresAnotados().size() < contexto.getCantidadJugadoresRequeridos()) {
			contexto.cambiarEstado(new EstadoBuscandoJugadores());
			System.out.println("Se bajó alguien, el partido ahora volvió a Buscando Jugadores");
		}
	}

	@Override
	public void confirmarJugador(Partido contexto) {
		System.out.println("Jugador confirmado para el partido.");
		boolean todosConfirmados = true;
		for (Usuario jugador : contexto.getJugadoresAnotados()) {
			if (!jugadorConfirmado(jugador)) {
				todosConfirmados = false;
				break;
			}
		}
		if (todosConfirmados) {
			contexto.cambiarEstado(new EstadoPartidoConfirmado());
			System.out.println("Todos confirmaron el partido! Partido pasa a estado Confirmado!");
		}
	}

	@Override
	public void cancelarPartido() {
		System.out.println("Partido armado ha sido cancelado. Que bajon");
	}
}