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

	public Usuario(String nombreUsuario, String correo, String password) {
		this.nombreUsuario = nombreUsuario;
		this.correo = correo;
		this.password = password;
		this.registrarse();
	}
	
	public void registrarse() {
		System.out.println("Usuario registrado: " + this.nombreUsuario);
	}

	public List<Partido> consultarHistorialPartidos() {
		return partidos;
	}

	public Partido buscarPartido(Ubicacion ubicacionDeseada) {
		Partido partidoCercano = null;
		double distanciaMinima = Double.MAX_VALUE;
		for (Partido partido : partidos) {
			double distancia = partido.getUbicacion().calcularDistancia(ubicacionDeseada);
			if (distancia < distanciaMinima) {
				distanciaMinima = distancia;
				partidoCercano = partido;
			}
		}
		return partidoCercano;
	}

	public boolean login(String nombreUsuario, String password) {
        return nombreUsuario.equals(this.nombreUsuario) && password.equals(this.password);
	}
}
