package Model;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import DTO.*;

@Data
public class Usuario {
	private String nombreUsuario;
	private String correo;
	private String password;
	private List<UsuarioDeporte> deportes;
	private Ubicacion ubicacion;
	private List<Partido> partidos;
	private boolean sesion;
	private int cantidadPartidosJugados; // Para la estrategia de emparejamiento por historial

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
		if (this.sesion) { // Corregido: se eliminó el .equals(true) para un primitivo boolean
			this.sesion = false;
			System.out.println("Sesion cerrada: " + nombreUsuario);
			return this.toDTO();
		}
        	return null;
	   }

	public UsuarioDTO toDTO() {
        	UsuarioDTO dto = new UsuarioDTO();
        	dto.setNombreUsuario(this.nombreUsuario);
        	dto.setCorreo(this.correo); // Añadido: faltaba este campo
		dto.setSesion(this.sesion); // Añadido: faltaba este campo
		dto.setCantidadPartidosJugados(this.cantidadPartidosJugados); // Añadido: faltaba este campo
        	dto.setUbicacion(this.ubicacion != null ? this.ubicacion.toDTO() : null);

		// Verificamos primero si deportes no es null
		if (this.deportes != null) {
			List<UsuarioDeporteDTO> deportesDTO = new ArrayList<>();
			// Iteramos manualmente en lugar de usar stream para evitar problemas si toDTO() no existe
			for (UsuarioDeporte ud : this.deportes) {
				deportesDTO.add(convertirUsuarioDeporteADTO(ud));
			}
			dto.setDeportes(deportesDTO);
		}

		// Convertimos partidos a DTO
		if (this.partidos != null) {
			dto.setHistorial(this.partidos.stream()
				.map(Partido::toDTO)
				.collect(Collectors.toList()));
		}
        	return dto;
    }

	// Método auxiliar para convertir UsuarioDeporte a DTO
	private UsuarioDeporteDTO convertirUsuarioDeporteADTO(UsuarioDeporte ud) {
		UsuarioDeporteDTO dto = new UsuarioDeporteDTO();
		dto.setNivel(ud.getNivel());

		// Crear el DTO del deporte
		DeporteDTO deporteDTO = new DeporteDTO();
		deporteDTO.setNombre(ud.getDeporte().getNombre());
		deporteDTO.setJugadoresMinimos(ud.getDeporte().getJugadoresMinimos());
		deporteDTO.setJugadoresMaximos(ud.getDeporte().getJugadoresMaximos());

		dto.setDeporte(deporteDTO);
		return dto;
	}
}
