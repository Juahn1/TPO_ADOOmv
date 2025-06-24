package State;

import Model.Partido;
import Model.Usuario;

public interface EstadoPartido {
	void setContexto(Partido partido);
	void agregarJugador(Usuario jugador);
	void eliminarJugador(Usuario jugador);
	void confirmarJugador();
	void cancelarPartido();

	default String getNombreEstado() {
		return this.getClass().getSimpleName();
	}
}