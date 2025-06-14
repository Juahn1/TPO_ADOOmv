package Controllers;

import Model.*;
import dtos.UsuarioDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UsuarioController {

	private Usuario usuario;
	private List<Usuario> usuarios = new ArrayList<>();

	public Usuario crearUsuario(UsuarioDTO usuarioDTO) {
		Usuario nuevoUsuario = new Usuario(usuarioDTO.getNombreUsuario(), usuarioDTO.getCorreo(), usuarioDTO.getPassword());
		nuevoUsuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
		nuevoUsuario.setUbicacion(usuarioDTO.getUbicacion());

		List<UsuarioDeporte> lista = new ArrayList<>();
		for (Deporte d : usuarioDTO.getDeportes()) {
			UsuarioDeporte ud = new UsuarioDeporte();
			ud.setDeporte(d);
			ud.setNivel(Nivel.INTERMEDIO);
			ud.setJugador(nuevoUsuario);
			lista.add(ud);
		}

		nuevoUsuario.setDeportes(lista);
		nuevoUsuario.setPartidos(new ArrayList<>());

		usuarios.add(nuevoUsuario);
		this.setUsuario(nuevoUsuario);
		usuario.registrarse();

		return nuevoUsuario;
	}

	public void iniciarSesion(String nombre, String contrasena) {

		boolean encontrado = false;

		for (Usuario u: usuarios) {
			if (usuario.login(nombre, contrasena)) {
				encontrado = true;
				this.setUsuario(u);
				System.out.println("Login exitoso para el usuario: " + u.getNombreUsuario());
			}
		}
		if (!encontrado)  {
			System.out.println("Usuario o contrasena incorrectos. Intente nuevamente");
		}

	}
}
