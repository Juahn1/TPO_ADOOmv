package Strategy;

import Model.Partido;
import Model.Usuario;

// Interfaz para estrategias
public interface EstrategiaEmparejamiento {
	boolean esCompatible(Usuario jugador, Partido partido);

	


}
