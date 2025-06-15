package Model;

import lombok.Data;

@Data
public class Ubicacion {
	private String ciudad;
	private String direccion;
	private double latitud;
	private double longitud;

	public Ubicacion(String ciudad, String direccion, double latitud, double longitud) {
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.latitud = latitud;
		this.longitud = longitud;
	}


	public double calcularDistancia(Ubicacion otraUbicacion) {
		double dx = this.latitud - otraUbicacion.latitud;
		double dy = this.longitud - otraUbicacion.longitud;
		return Math.sqrt(dx * dx + dy * dy);
	}
}