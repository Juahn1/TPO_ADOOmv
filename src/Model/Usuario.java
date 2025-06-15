package Model;

import lombok.Data;import lombok.Data;
import java.util.List;

@Data
public class Usuario {
	private String nombreUsuario;
	private String correo;
	private String password;
	private List<UsuarioDeporte> deportes;
	private Ubicacion ubicacion;
	private List<Partido> partidos;
	private boolean sesion;

	public Usuario(String nombreUsuario, String correo, String password) {
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
		this.password = password;
		this.registrarse();
	}
	
	public UsuarioDTO registrarse(List<Usuario> listaUsuarios) {
	        listaUsuarios.add(this);
	        System.out.println("Usuario registrado: " + this.nombreUsuario);
        return this.toDTO();
    	}

	public List<PartidoDTO> consultarHistorialPartidos() {
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
		if (this.sesion.equals(true)) {
			this.sesion = false;
			System.out.println("Sesion cerrada: " + nombreUsuario);
			return this.toDTO();
		}
        	return null;
	   }

	public UsuarioDTO toDTO() {
        	UsuarioDTO dto = new UsuarioDTO();
        	dto.setNombreUsuario(this.nombreUsuario);
        	dto.setUbicacion(this.ubicacion != null ? this.ubicacion.toDTO() : null);
		dto.setDeportes(this.deportes != null ? this.deportes.stream()
                	.map(UsuarioDeporte::toDTO)
                	.collect(Collectors.toList()) : null);
       		dto.setHistorial(this.partidos != null ? this.partidos.stream()
                	.map(Partido::toDTO)
                	.collect(Collectors.toList()) : null);
        	return dto;
    }
}
