package Strategy;

import Model.Nivel;
import Model.Partido;
import Model.Usuario;
import Model.UsuarioDeporte;

public class EstrategiaEmparejamientoNivel implements EstrategiaEmparejamiento {

	@Override
	public boolean esCompatible(Usuario jugador, Partido partido) {
		Nivel nivelJugador = Nivel.PRINCIPIANTE; // Nivel por defecto si no tiene el deporte

		if (jugador.getDeportes() != null) {
			for (UsuarioDeporte ud : jugador.getDeportes()) {
				if (ud.getDeporte().getNombre().equals(partido.getDeporte().getNombre())) {
					nivelJugador = ud.getNivel();
					break;
				}
			}
		}

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
