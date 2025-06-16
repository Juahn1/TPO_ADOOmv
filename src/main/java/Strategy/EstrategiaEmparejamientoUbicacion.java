package Strategy;

import Model.Partido;
import Model.Usuario;
import Model.Ubicacion;

public class EstrategiaEmparejamientoUbicacion implements EstrategiaEmparejamiento {

	private static final double UMBRAL_KM = 10.0; // Distancia máxima en kilómetros (Se supuso así)

	@Override
	public boolean esCompatible(Usuario jugador, Partido partido) {
		double distancia = jugador.getUbicacion().calcularDistancia(partido.getUbicacion());
		double distanciaEnKm = distancia * 111.0;

		return distanciaEnKm <= UMBRAL_KM;
	}
}
