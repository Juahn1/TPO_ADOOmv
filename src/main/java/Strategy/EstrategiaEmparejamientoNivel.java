package Strategy;

import Model.Nivel;
import Model.Partido;
import Model.Usuario;
import Model.UsuarioDeporte;

public class EstrategiaEmparejamientoNivel implements EstrategiaEmparejamiento {

	@Override
	public boolean esCompatible(Usuario jugador, Partido partido) {
		Nivel nivelJugador = Nivel.AVANZADO; // Nivel por defecto si no tiene el deporte

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
}
