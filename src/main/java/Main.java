public class Main {
	public static void main(String[] args) {
		//1. Registrar un usuario

		//Crear la ubicación primero
		Ubicacion ubicacion = new Ubicacion("Ciudad", "Dirección", 0.0, 0.0);

		//Instanciar un nuevo usuario
		Usuario tomas = new Usuario();
		tomas.setNombreUsuario("Tomas");  // Corrected method name
		tomas.setPassword("password");    // Corrected method name
		tomas.setCorreo("tomas@gmail.com"); // Corrected method name
		tomas.setUbicacion(ubicacion);

		//2. Buscar partidos disponibles
		//3. Crear un partido
		//4. Gestionar estados del partido
		//5. Emparejamiento de jugadores
		//6. Recibir notificaciones
	}
}