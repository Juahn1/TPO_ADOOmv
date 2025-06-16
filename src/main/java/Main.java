import Model.*;
import State.*;
import Strategy.*;
import State.*;
import Adapter.*;

public class Main {
	public static <INotificacionAdapter> void main(String[] args) {
		// 1. Registrar un usuario
		Ubicacion ubicacion = new Ubicacion("Ciudad", "Dirección", 0.0, 0.0);
		Usuario tomas = new Usuario();
		tomas.setNombreUsuario("Tomas");
		tomas.setPassword("password");
		tomas.setCorreo("tomas@gmail.com");
		tomas.setUbicacion(ubicacion);

		// 2. Buscar partidos disponibles
		Ubicacion ubi1 = new Ubicacion("CABA", "Dorrego 123", -34.6037, -58.3816);
		Ubicacion ubi2 = new Ubicacion("CABA", "Cabildo 222", -34.5622, -58.4563);

		Deporte futbol = new Deporte("Fútbol", 5, 11);
		Usuario organizador = tomas;

		EstrategiaEmparejamiento estrategiaNivel = new EstrategiaEmparejamientoNivel();
		Partido partido1 = new Partido(ubi1, futbol, 10, 60, organizador, estrategiaNivel);
		Partido partido2 = new Partido(ubi2, futbol, 8, 45, organizador, estrategiaNivel);

		// 3. Crear un partido
		EstrategiaEmparejamiento estrategiaUbicacion = new EstrategiaEmparejamientoUbicacion();
		Partido nuevoPartido = new Partido(
				new Ubicacion("Parque Roca", "Calle X", 2.5, 2.5),
				futbol,
				6,
				90,
				organizador,
				estrategiaUbicacion
		);
		nuevoPartido.setNivelMinimo(Nivel.INTERMEDIO);
		nuevoPartido.setNivelMaximo(Nivel.AVANZADO);
		System.out.println("Nuevo partido creado en: " + nuevoPartido.getUbicacion());

		// 4. Gestionar estados del partido
		System.out.println("Estado inicial: " + nuevoPartido.getEstado().getNombreEstado());
		nuevoPartido.cambiarEstado(new EstadoPartidoConfirmado());
		System.out.println("Estado actualizado: " + nuevoPartido.getEstado().getNombreEstado());
		nuevoPartido.cambiarEstado(new EstadoPartidoEnJuego());
		System.out.println("Estado actualizado: " + nuevoPartido.getEstado().getNombreEstado());
		nuevoPartido.cambiarEstado(new EstadoPartidoFinalizado());
		System.out.println("Estado final del partido: " + nuevoPartido.getEstado().getNombreEstado());

		// 5. Emparejamiento de jugadores
		boolean compatible = estrategiaUbicacion.esCompatible(tomas, nuevoPartido);
		System.out.println("¿El usuario es compatible con el partido?: " + compatible);

		// 6. Recibir notificaciones usando Adapter
		INotificacionAdapter mailAdapter = new MailAdapter(); // Instancia del adapter
		Notificador notificador = new Notificador(mailAdapter); // Inyección del adapter
		notificador.notificar(tomas, "¡Hay un nuevo partido disponible!");
	}
}
