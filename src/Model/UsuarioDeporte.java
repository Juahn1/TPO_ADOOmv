package Model;

import lombok.Data;

@Data
public class UsuarioDeporte {
	private Nivel nivel;
	private Deporte deporte;
	private Usuario jugador;
}
