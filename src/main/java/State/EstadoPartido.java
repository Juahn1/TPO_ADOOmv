package State;

import Model.Partido;

public interface EstadoPartido {
	void agregarJugador(Partido partido);
	void eliminarJugador(Partido partido);
	void confirmarJugador(Partido partido);
	void cancelarPartido();

	default String getNombreEstado() {
		return this.getClass().getSimpleName();
	}
}