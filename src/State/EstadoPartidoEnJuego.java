package Model;

public class EstadoPartidoEnJuego extends EstadoPartido {

	@Override
	public void agregarJugador(Partido contexto) {
		System.out.println("No se pueden agregar jugadores a un partido en juego. Cualquiera lo tuyo");
	}

	@Override
	public void eliminarJugador(Partido contexto) {
		System.out.println("El partido está en juego, no se pueden eliminar jugadores.");
	}

	@Override
	public void confirmarJugador(Partido contexto) {
		System.out.println("El partido está en juego, no se pueden confirmar jugadores..");
	}

	@Override
	public void cancelarPartido() {
		System.out.println("El partido está en juego, no se pueden cancelar (?.");
	}

	public void finalizarPartido(Partido contexto) {
		contexto.cambiarEstado(new EstadoPartidoFinalizado());
		System.out.println("El partido terminó, y pasó a estado: Finalizado");
	}
}