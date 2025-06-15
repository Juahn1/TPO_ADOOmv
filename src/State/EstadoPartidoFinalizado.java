package Model;

public class EstadoPartidoFinalizado extends EstadoPartido {

	@Override
	public void agregarJugador(Partido contexto) {
		System.out.println("No se pueden agregar jugadores a un partido finalizado.");
	}

	@Override
	public void eliminarJugador(Partido contexto) {
		System.out.println("No se pueden eliminar jugadores de un partido finalizado.");
	}

	@Override
	public void confirmarJugador(Partido contexto) {
		System.out.println("No se pueden confirmar jugadores en un partido finalizado.");
	}

	@Override
	public void cancelarPartido() {
		System.out.println("No se puede cancelar un partido finalizado.");
	}
}