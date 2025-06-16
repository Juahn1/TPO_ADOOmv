package State;

import Model.Partido;
import Model.Usuario;

public class EstadoPartidoArmado extends EstadoPartido {

	@Override
	public void agregarJugador(Partido partido) {
		System.out.println("No se puede! El partido ya tiene a todos sus jugadores anotados y está armado!");
	}

	@Override
	public void eliminarJugador(Partido partido) {
		System.out.println("Jugador eliminado del partido armado.");
		if (partido.getJugadoresAnotados().size() < partido.getCantidadJugadoresRequeridos()) {
			partido.cambiarEstado(new EstadoBuscandoJugadores());
			System.out.println("Se bajó alguien, el partido ahora volvió a Buscando Jugadores");
		}
	}

	@Override
	public void confirmarJugador(Partido partido) {
		System.out.println("Jugador confirmado para el partido.");
		boolean todosConfirmados = true;
		for (Usuario jugador : partido.getJugadoresAnotados()) {
			if (!jugadorConfirmado(jugador)) {
				todosConfirmados = false;
				break;
			}
		}
		if (todosConfirmados) {
			partido.cambiarEstado(new EstadoPartidoConfirmado());
			System.out.println("Todos confirmaron el partido! Partido pasa a estado Confirmado!");
		}
	}

	@Override
	public void cancelarPartido() {
		System.out.println("Partido armado ha sido cancelado. Que bajon");
	}
}