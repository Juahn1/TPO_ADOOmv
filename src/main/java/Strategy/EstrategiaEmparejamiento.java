package Strategy;

import Model.Partido;
import Model.Usuario;

// Interfaz para estrategias de emparejamiento
public interface EstrategiaEmparejamiento {
	boolean esCompatible(Usuario jugador, Partido partido);
	void emparejar(Partido partido);
}
