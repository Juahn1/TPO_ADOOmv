package Strategy;

import Model.Nivel;
import Model.Partido;
import Model.Usuario;

public class EstrategiaEmparejamientoNivel implements EstrategiaEmparejamiento {

	@Override
	public boolean esCompatible(Usuario jugador, Partido partido) {
		Nivel nivelJugador = jugador.getNivel();
		Nivel nivelMinimo = partido.getNivelMinimo();
		Nivel nivelMaximo = partido.getNivelMaximo();

		return (nivelJugador.ordinal() >= nivelMinimo.ordinal() &&
				nivelJugador.ordinal() <= nivelMaximo.ordinal());
	}

	@Override
	public void emparejar(Partido partido) {
		System.out.println("Emparejando jugadores por nivel de habilidad para el partido: " + partido.getId());
		// Implementación del algoritmo de emparejamiento basado en nivel
		// Por ejemplo: buscar jugadores con niveles similares para balancear equipos
	}
}
