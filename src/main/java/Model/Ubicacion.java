package Model;

import jakarta.persistence.*;
import lombok.*;
import DTO.UbicacionDTO;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ubicacion {

	@Id
	@GeneratedValue
	private long id;

	private String ciudad;
	private String direccion;
	private double latitud;
	private double longitud;




	public double calcularDistancia(Ubicacion otraUbicacion) {
		double dx = this.latitud - otraUbicacion.latitud;
		double dy = this.longitud - otraUbicacion.longitud;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public UbicacionDTO toDTO() {
		return UbicacionDTO.builder()
				.latitud(this.latitud)
				.longitud(this.longitud)
				.direccion(this.direccion)
				.ciudad(this.ciudad)
				.build();

	}

}