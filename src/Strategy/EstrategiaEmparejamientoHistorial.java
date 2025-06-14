package Strategy;

import Model.Partido;
import Model.Usuario;

public class EstrategiaEmparejamientoHistorial extends EstrategiaEmparejamiento{

	public boolean esCompatible(Jugador jugador, Partido partido) {
		int historial = jugador.getCantidadPartidosJugados();
		// Reglas hipotéticas: mínimo 5 partidos
		return historial >= partido.getMinPartidosRequeridos();
	}
	
	public void emparejar() {
		
	}

}
