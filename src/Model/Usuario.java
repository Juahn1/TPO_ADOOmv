package Model;

import java.util.List;

public class Usuario {
	//Atributos
	private String nombreUsuario;
	private String correo;
	private String password;
	private List<Deporte> deportes;
	private List<Ubicacion> ubicacion;
	private List<Partido> partidos;

	//Metodos
	public void registrarse() {
		
	};
	
	public List<Partido> consultarHistorialPartidos(){
		return partidos;
		
	};
	
	public Partido buscarPartido() {
		return null;
		
	};
	
	public void logIn() {
		
	};

}
