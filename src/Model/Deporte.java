package Model;

import lombok.Data;

@Data
public class Deporte {
	private String nombre;
	private int jugadoresMaximos;
	private int jugadoresMinimos;

	public Deporte(String nombre, int jugadoresMinimos, int jugadoresMaximos) {
		this.nombre = nombre;
		this.jugadoresMinimos = jugadoresMinimos;
		this.jugadoresMaximos = jugadoresMaximos;
	}
}
