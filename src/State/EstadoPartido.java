package Model;

public abstract class EstadoPartido {
	public abstract void agregarJugador(Partido contexto);
	public abstract void eliminarJugador(Partido contexto);
	public abstract void confirmarJugador(Partido contexto);
	public abstract void cancelarPartido();

	public String getNombreEstado() {
		return this.getClass().getSimpleName();
	}
}