package DTO;
import lombok.*;
import Model.Deporte;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartidoDTO {
	private String id;
	private String nombreDeporte;

	@Builder.Default
	private int cantidadJugadoresAnotados = 1;

	private int cantidadJugadoresRequeridos;

	@Builder.Default
	private String estado = "Necesitamos jugadores";

	private UbicacionDTO ubicacion;
	private int duracion;
	private String fechaHora;
}

