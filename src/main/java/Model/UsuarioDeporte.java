package Model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usuario_deporte")

public class UsuarioDeporte {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Nivel nivel;

	@ManyToOne
	@JoinColumn(name = "deporte_id")
	private Deporte deporte;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario jugador;
}
