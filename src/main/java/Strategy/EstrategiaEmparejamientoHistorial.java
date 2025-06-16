package Strategy;

import Model.Partido;
import Model.Usuario;

public class EstrategiaEmparejamientoHistorial implements EstrategiaEmparejamiento {

	@Override
	public boolean esCompatible(Usuario jugador, Partido partido) {
		int historial = jugador.getCantidadPartidosJugados();
		return historial >= partido.getMinPartidosRequeridos();
	}
	
	@Override
	public void emparejar(Partido partido) {
		System.out.println("Emparejando jugadores por historial de partidos para el partido: " + partido.getId());
	}
}
