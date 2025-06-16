package Strategy;

import Model.Partido;
import Model.Usuario;

public class EstrategiaEmparejamientoUbicacion extends EstrategiaEmparejamiento{

	public boolean esCompatible(Jugador jugador, Partido partido) {
		double distancia = calcularDistancia(jugador.getLatitud(), jugador.getLongitud(),
				partido.getLatitud(), partido.getLongitud());
		return distancia <= UMBRAL_KM;
	}

	private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
		final int R = 6371; // Radio de la Tierra en km
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return R * c;
	}
		
	public void emparejar() {
			
		}

}
