package Controllers;

import lombok.Data;
import Model.*;
import Strategy.*;
import DTO.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class PartidoController {
	
	private final List<Partido> partidos;
	private final List<Notificador> notificadores;
	private final Map<String, Deporte> deportes;

	public PartidoController(List<Partido> partidos, List<Notificador> notificadores, Map<String, Deporte> deportes) {
		this.partidos = partidos;
		this.notificadores = notificadores;
		this.deportes = deportes;
	}

	public PartidoDTO crearPartido(String nombreDeporte, int cantidadJugadores, int duracion,
                                  UbicacionDTO ubicacionDTO, Usuario organizador,
                                  String estrategia, LocalDateTime fechaHora) {
		Ubicacion ubicacion = new Ubicacion(
			ubicacionDTO.getCiudad(),
			ubicacionDTO.getDireccion(),
			ubicacionDTO.getLatitud(),
			ubicacionDTO.getLongitud()
		);

		Deporte deporte = deportes.get(nombreDeporte);
		if (deporte == null) {
			System.out.println("No se encontró el deporte: " + nombreDeporte);
			return null;
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
				estrategiaEmparejamiento = new EstrategiaEmparejamientoNivel();
		}

		Partido partido = new Partido(
			ubicacion,
			deporte,
			cantidadJugadores,
			duracion,
			organizador,
			estrategiaEmparejamiento,
			fechaHora
		);

		partido.setId(generateUniqueId());
		for (Notificador notificador : notificadores) {
			partido.agregarObservador(notificador);
		}
		partidos.add(partido);
		partido.notificar();
		return partido.toDTO();
	}

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

	public PartidoDTO agregarJugadorAPartido(String idPartido, Usuario jugador) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null;
		}

		if (!partido.getEstrategiaEmparejamiento().esCompatible(jugador, partido)) {
			return null;
		}

		partido.agregarJugador(jugador);
		return partido.toDTO();
	}

	public PartidoDTO eliminarJugadorDePartido(String idPartido, Usuario jugador) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null;
		}

		partido.eliminarJugador(jugador);
		return partido.toDTO();
	}

	public PartidoDTO confirmarJugadorEnPartido(String idPartido, Usuario jugador) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null;
		}

		partido.confirmarJugador(jugador);
		return partido.toDTO();
	}

	public PartidoDTO confirmarPartido(String idPartido) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null;
		}

		partido.confirmarJugadores();
		return partido.toDTO();
	}

	public PartidoDTO cancelarPartido(String idPartido) {
		Partido partido = buscarPartido(idPartido);
		if (partido == null) {
			return null;
		}
		partido.cancelarPartido();
		return partido.toDTO();
	}

	public List<PartidoDTO> obtenerTodosLosPartidos() {
		return partidos.stream()
			.map(Partido::toDTO)
			.collect(Collectors.toList());
	}

	public void verificarEstadosPartidos() {
		for (Partido partido : partidos) {
			partido.verificarEstadoActual();
		}
	}

	public void agregarNotificador(Notificador notificador) {
		notificadores.add(notificador);
	}
	private Deporte buscarDeporte(String nombreDeporte) {
		return deportes.get(nombreDeporte);
	}
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
	private int generateUniqueId() {
		return partidos.size() + 1;
	}
}
