package Strategy;

import Model.Partido;
import Model.Usuario;

public class EstrategiaEmparejamientoHistorial implements EstrategiaEmparejamiento {

	@Override
	public boolean esCompatible(Usuario jugador, Partido partido) {
		int historial = jugador.getCantidadPartidosJugados();
		return historial >= partido.getMinPartidosRequeridos();
	}
}
