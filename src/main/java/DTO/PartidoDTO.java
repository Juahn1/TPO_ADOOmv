package DTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import Model.Deporte;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartidoDTO {
	private String id;
	private String nombreDeporte;
	private int cantidadJugadoresAnotados;
	private int cantidadJugadoresRequeridos;
	private String estado;
	private UbicacionDTO ubicacion;
	private int duracion;
	private String fechaHora;

	// Constructor para crear un nuevo partido
	public PartidoDTO(String id, Deporte deporte, int cantidadJugadoresRequeridos, int duracion, UbicacionDTO ubicacion, String fechaHora) {
		this.id = id;
		this.nombreDeporte = deporte.getNombre();
		this.cantidadJugadoresRequeridos = cantidadJugadoresRequeridos;
		this.duracion = duracion;
		this.ubicacion = ubicacion;
		this.fechaHora = fechaHora;
		this.estado = "Necesitamos jugadores";
		this.cantidadJugadoresAnotados = 1; // El organizador es el primer jugador
	}
}
