package Strategy;

import Model.Partido;
import Model.Usuario;

// Interfaz para estrategias de emparejamiento
public interface EstrategiaEmparejamiento {
	/**
	 * Evalúa si un jugador es compatible con un partido según la estrategia
	 * @param jugador El usuario que se quiere unir al partido
	 * @param partido El partido al que se quiere unir
	 * @return true si el jugador es compatible, false en caso contrario
	 */
	boolean esCompatible(Usuario jugador, Partido partido);

	/**
	 * Implementación del algoritmo de emparejamiento específico
	 * @param partido El partido para el cual se emparejan jugadores
	 */
	void emparejar(Partido partido);
}
