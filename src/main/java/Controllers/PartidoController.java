package Controllers;

import lombok.Data;
import Model.*;
import Strategy.*;
import DTO.PartidoDTO;
import DTO.UbicacionDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class PartidoController {
	
	private final List<Partido> partidos;
	private final List<Notificador> notificadores;
	private final Map<String, Deporte> deportes; // Mapa de deportes disponibles

	public PartidoController(List<Partido> partidos, List<Notificador> notificadores, Map<String, Deporte> deportes) {
		this.partidos = partidos;
		this.notificadores = notificadores;
		this.deportes = deportes;
	}

	public PartidoDTO crearPartido(String nombreDeporte, int cantidadJugadores, int duracion,
                                  UbicacionDTO ubicacionDTO, Usuario organizador,
                                  String estrategia, LocalDateTime fechaHora) {
		// Convertir UbicacionDTO a Ubicacion
		Ubicacion ubicacion = new Ubicacion(
			ubicacionDTO.getCiudad(),
			ubicacionDTO.getDireccion(),
			ubicacionDTO.getLatitud(),
			ubicacionDTO.getLongitud()
		);

		Deporte deporte = deportes.get(nombreDeporte);
		if (deporte == null) {
			System.out.println("No se encontró el deporte: " + nombreDeporte);
			return null; // No se encontró el deporte
		}

		EstrategiaEmparejamiento estrategiaEmparejamiento;
		switch (estrategia.toLowerCase()) {
			case "nivel":
				estrategiaEmparejamiento = new EstrategiaEmparejamientoNivel();
				break;
			case "historial":
				estrategiaEmparejamiento = new EstrategiaEmparejamientoHistorial();
				break;
			case "ubicacion":
				estrategiaEmparejamiento = new EstrategiaEmparejamientoUbicacion();
				break;
			default:
				estrategiaEmparejamiento = new EstrategiaEmparejamientoNivel(); // Por defecto
		}

		// Crear el partido
		Partido partido = new Partido(
			ubicacion,
			deporte,
			cantidadJugadores,
			duracion,
			organizador,
			estrategiaEmparejamiento,
			fechaHora
		);

		// Asignar un ID único
		partido.setId(generateUniqueId());

		// Agregar los notificadores registrados
		for (Notificador notificador : notificadores) {
			partido.agregarObservador(notificador);
		}

		// Guardar el partido en la lista
		partidos.add(partido);

		// Notificar la creación del partido
		partido.notificar();

		// Devolver el DTO del partido
		return partido.toDTO();
	}

	// Método para buscar partidos por ubicación
	public List<PartidoDTO> buscarPartidosPorUbicacion(UbicacionDTO ubicacionDTO, double distanciaMaxima) {
		Ubicacion ubicacionBusqueda = new Ubicacion(
			ubicacionDTO.getCiudad(),
			ubicacionDTO.getDireccion(),
			ubicacionDTO.getLatitud(),
			ubicacionDTO.getLongitud()
		);

		return partidos.stream()
			.filter(partido -> partido.getUbicacion().calcularDistancia(ubicacionBusqueda) <= distanciaMaxima)
			.map(Partido::toDTO)
			.collect(Collectors.toList());
	}

	// Método para agregar un jugador a un partido
	public PartidoDTO agregarJugadorAPartido(String idPartido, Usuario jugador) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null; // No se encontró el partido
		}

		// Verificar compatibilidad según la estrategia
		if (!partido.getEstrategiaEmparejamiento().esCompatible(jugador, partido)) {
			return null; // El jugador no es compatible según la estrategia
		}

		partido.agregarJugador(jugador);
		return partido.toDTO();
	}

	// Método para eliminar un jugador de un partido
	public PartidoDTO eliminarJugadorDePartido(String idPartido, Usuario jugador) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null; // No se encontró el partido
		}

		partido.eliminarJugador(jugador);
		return partido.toDTO();
	}

	// Método para confirmar un partido
	public PartidoDTO confirmarPartido(String idPartido) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null; // No se encontró el partido
		}

		partido.confirmarJugadores();
		return partido.toDTO();
	}

	// Método para cancelar un partido
	public PartidoDTO cancelarPartido(String idPartido) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null; // No se encontró el partido
		}

		partido.cancelarPartido();
		return partido.toDTO();
	}

	// Método para obtener todos los partidos
	public List<PartidoDTO> obtenerTodosLosPartidos() {
		return partidos.stream()
			.map(Partido::toDTO)
			.collect(Collectors.toList());
	}

	// Método para verificar y actualizar estados de partidos
	public void verificarEstadosPartidos() {
		for (Partido partido : partidos) {
			partido.verificarEstadoActual();
		}
	}

	// Método para agregar un notificador que se aplicará a todos los partidos nuevos
	public void agregarNotificador(Notificador notificador) {
		notificadores.add(notificador);
	}

	// Método para buscar un deporte por nombre
	private Deporte buscarDeporte(String nombreDeporte) {
		return deportes.get(nombreDeporte);
	}

	// Método para buscar un partido por ID
	private Partido buscarPartido(String idPartido) {
		try {
			int id = Integer.parseInt(idPartido);
			return partidos.stream()
				.filter(p -> p.getId() == id)
				.findFirst()
				.orElse(null);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	// Método para generar un ID único
	private int generateUniqueId() {
		return partidos.size() + 1;
	}
}
