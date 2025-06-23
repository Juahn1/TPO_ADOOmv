package Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deporte {



	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;


	@Column(nullable = false, unique = true)
	private String nombre;
	@Column(nullable = false)
	private int jugadoresMaximos;

	@Column(nullable = false)
	private int jugadoresMinimos;


}
