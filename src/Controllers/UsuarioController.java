package Controllers;

import Model.*;
import dtos.UsuarioDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UsuarioController {

	private Usuario usuario;
	
	public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO, List<Usuario> usuarios) {
		
		Usuario nuevoUsuario = new Usuario(
		    usuarioDTO.getNombreUsuario(),
		    usuarioDTO.getCorreo(),
		    usuarioDTO.getPassword()
		);
		
		if (usuarioDTO.getUbicacion() != null) {
		    Ubicacion ubicacion = new Ubicacion();
		    ubicacion.setLatitud(usuarioDTO.getUbicacion().getLatitud());
		    ubicacion.setLongitud(usuarioDTO.getUbicacion().getLongitud());
		    nuevoUsuario.setUbicacion(ubicacion);
		}
	
		List<UsuarioDeporte> lista = new ArrayList<>();
		for (UsuarioDeporteDTO dto : usuarioDTO.getDeportes()) {
		    UsuarioDeporte ud = new UsuarioDeporte();
		    ud.setNivel(dto.getNivel());
	
		    Deporte d = new Deporte();
		    d.setNombre(dto.getDeporte().getNombre());
		    d.setJugadoresMaximos(dto.getDeporte().getJugadoresMaximos());
		    d.setJugadoresMinimos(dto.getDeporte().getJugadoresMinimos());
	
		    ud.setDeporte(d);
		    ud.setJugador(nuevoUsuario);
		    lista.add(ud);
		}
	
		nuevoUsuario.setDeportes(lista);
		nuevoUsuario.setPartidos(new ArrayList<>());
	
		this.setUsuario(nuevoUsuario);

        	return nuevoUsuario.registrarse(usuarios);
    	}
	
	public UsuarioDTO iniciarSesion(String nombre, String contrasena) {
		        for (Usuario u : usuarios) {
		            UsuarioDTO dto = u.login(nombre, contrasena);
		            if (dto != null) {
		                this.setUsuario(u);
		                return dto;
		            }
		        }
		        System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
	        return null;
    	}

	
    public UsuarioDTO iniciarSesion(String nombre, String contrasena) {
	for (Usuario u : usuarios) {
	    UsuarioDTO dto = u.login(nombre, contrasena);
	    if (dto != null) {
		this.setUsuario(u);
		return dto;
	    }
	}
	System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
	return null;
    }

    public UsuarioDTO cerrarSesion() {
        if (usuario != null) {
            return usuario.logout();
        }
        System.out.println("No hay sesión activa.");
        return null;
    }

    public List<PartidoDTO> consultarHistorial() {
        if (usuario != null) {
            return usuario.consultarHistorialPartidos();
        }
        System.out.println("No hay usuario logueado.");
        return new ArrayList<>();
    }

    public PartidoDTO buscarPartido(UbicacionDTO ubicacionDTO) {
        if (usuario != null && ubicacionDTO != null) {
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setLatitud(ubicacionDTO.getLatitud());
            ubicacion.setLongitud(ubicacionDTO.getLongitud());
            return usuario.buscarPartido(ubicacion);
        }
        System.out.println("Debe iniciar sesión y proporcionar una ubicación válida.");
        return null;
    }

    public UsuarioDTO getUsuarioActual() {
        return usuario != null ? usuario.toDTO() : null;
    }
}
