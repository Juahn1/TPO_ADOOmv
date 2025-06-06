package Strategy;

import Model.Partido;
import Model.Usuario;

public class EmparejamientoPorNivel implements EstrategiaEmparejamiento {


	@Override
	public boolean esCompatible(Jugador jugador, Partido partido) {
		Nivel nivelJugador = jugador.getNivel();
		Nivel nivelMinimo = partido.getNivelMinimo();
		Nivel nivelMaximo = partido.getNivelMaximo();

		return (nivelJugador.ordinal() >= nivelMinimo.ordinal() &&
				nivelJugador.ordinal() <= nivelMaximo.ordinal());
	}
}


}
