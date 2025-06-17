package Model;

import jakarta.persistence.*;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import DTO.*;

@Data
@Entity
@Table(name = "Usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	private String nombreUsuario;
	private String correo;
	private String password;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UsuarioDeporte> deportes;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ubicacion_id")
	private Ubicacion ubicacion;

	@OneToMany(mappedBy = "organizador", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Partido> partidos;

	private boolean sesion;
	private int cantidadPartidosJugados; // Esta variable solo se usa para la estrategia de emparejar por historial de partidos.

	public Usuario(String nombreUsuario, String correo, String password) {
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
		this.password = password;
	}

	public List<PartidoDTO> consultarHistorialPartidos() {
		if (partidos == null || partidos.isEmpty()) {
			return new ArrayList<>();
		}
        return partidos.stream().map(Partido::toDTO).collect(Collectors.toList());
	}

	public PartidoDTO buscarPartido(Ubicacion ubicacionDeseada) {
        	
		Partido partidoCercano = null;
        	double distanciaMinima = Double.MAX_VALUE;

        	for (Partido partido : partidos) {
           		double distancia = partido.getUbicacion().calcularDistancia(ubicacionDeseada);
		   	if (distancia < distanciaMinima) {
				distanciaMinima = distancia;
				partidoCercano = partido;
		    	}
        	}

        	return partidoCercano != null ? partidoCercano.toDTO() : null;
    	}	

	public UsuarioDTO login(String nombreUsuario, String password) {
			if (nombreUsuario.equals(this.nombreUsuario) && password.equals(this.password)) {
			    this.sesion = true;
			    System.out.println("Login exitoso para el usuario: " + nombreUsuario);
			    return this.toDTO();
			}
        	return null;
	   }

	public UsuarioDTO logout() {
		if (this.sesion) {
			this.sesion = false;
			System.out.println("Sesion cerrada: " + nombreUsuario);
			return this.toDTO();
		}
        	return null;
	   }

	public UsuarioDTO toDTO() {
        	UsuarioDTO dto = new UsuarioDTO();
        	dto.setNombreUsuario(this.nombreUsuario);
        	dto.setCorreo(this.correo);
		dto.setSesion(this.sesion);
		dto.setCantidadPartidosJugados(this.cantidadPartidosJugados);
        	dto.setUbicacion(this.ubicacion != null ? this.ubicacion.toDTO() : null);


		if (this.deportes != null) {
			List<UsuarioDeporteDTO> deportesDTO = new ArrayList<>();
			for (UsuarioDeporte ud : this.deportes) {
				deportesDTO.add(convertirUsuarioDeporteADTO(ud));
			}
			dto.setDeportes(deportesDTO);
		}

		if (this.partidos != null) {
			dto.setHistorial(this.partidos.stream()
				.map(Partido::toDTO)
				.collect(Collectors.toList()));
		}
        	return dto;
    }

	private UsuarioDeporteDTO convertirUsuarioDeporteADTO(UsuarioDeporte ud) {
		UsuarioDeporteDTO dto = new UsuarioDeporteDTO();
		dto.setNivel(ud.getNivel());
		DeporteDTO deporteDTO = new DeporteDTO();
		deporteDTO.setNombre(ud.getDeporte().getNombre());
		deporteDTO.setJugadoresMinimos(ud.getDeporte().getJugadoresMinimos());
		deporteDTO.setJugadoresMaximos(ud.getDeporte().getJugadoresMaximos());

		dto.setDeporte(deporteDTO);
		return dto;
	}
}
