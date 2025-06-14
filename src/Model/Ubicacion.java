package Model;

import lombok.Data;

@Data
public class Ubicacion {
	private double latitud;
	private double longitud;

	public double calcularDistancia(Ubicacion otraUbicacion) {
		double dx = this.latitud - otraUbicacion.latitud;
		double dy = this.longitud - otraUbicacion.longitud;
		return Math.sqrt(dx * dx + dy * dy);
	}
}