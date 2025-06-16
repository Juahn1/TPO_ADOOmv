package State;

import Model.Partido;

public abstract class EstadoPartido {
	public abstract void agregarJugador(Partido partido);
	public abstract void eliminarJugador(Partido partido);
	public abstract void confirmarJugador(Partido partido);
	public abstract void cancelarPartido();

	public String getNombreEstado() {
		return this.getClass().getSimpleName();
	}
}