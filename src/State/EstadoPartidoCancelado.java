package Model;

public class EstadoPartidoCancelado extends EstadoPartido {

	@Override
	public void agregarJugador(Partido contexto) {
		System.out.println("No se pueden agregar jugadores a un partido cancelado.");
	}

	@Override
	public void eliminarJugador(Partido contexto) {
		System.out.println("No se pueden eliminar jugadores de un partido cancelado.");
	}

	@Override
	public void confirmarJugador(Partido contexto) {
		System.out.println("No se pueden confirmar jugadores en un partido cancelado.");
	}

	@Override
	public void cancelarPartido() {
		System.out.println("El partido ya está cancelado.");
	}
}