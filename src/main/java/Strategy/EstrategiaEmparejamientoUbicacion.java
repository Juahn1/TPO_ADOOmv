package Strategy;

import Model.Partido;
import Model.Usuario;
import Model.Ubicacion;

public class EstrategiaEmparejamientoUbicacion implements EstrategiaEmparejamiento {

	private static final double UMBRAL_KM = 10.0; // Distancia máxima en kilómetros

	@Override
	public boolean esCompatible(Usuario jugador, Partido partido) {
		// Utilizamos directamente el método calcularDistancia de la clase Ubicacion
		double distancia = jugador.getUbicacion().calcularDistancia(partido.getUbicacion());

		// Convertimos la distancia (que es una aproximación euclidiana) a kilómetros
		// suponiendo que estamos trabajando con coordenadas GPS en grados
		// Factor de conversión aproximado: 1 grado ≈ 111 km
		double distanciaEnKm = distancia * 111.0;

		return distanciaEnKm <= UMBRAL_KM;
	}

	@Override
	public void emparejar(Partido partido) {
		System.out.println("Emparejando jugadores por cercanía geográfica para el partido: " + partido.getId());
		// Implementación: buscar jugadores cercanos para formar equipos equilibrados
	}
}
