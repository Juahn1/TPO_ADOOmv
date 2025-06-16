import Model.*;
import State.*;
import Strategy.*;
import Adapter.*;
import Controllers.*;
import DTO.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Partido> partidos = new ArrayList<>();
    private static List<Notificador> notificadores = new ArrayList<>();
    private static Map<String, Deporte> mapDeportes = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        inicializarDatos();
        UsuarioController usuarioController = new UsuarioController(usuarios);
        PartidoController partidoController = new PartidoController(partidos, notificadores, mapDeportes);

        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = obtenerOpcionUsuario(scanner);

            switch (opcion) {
                case 1:
                    registrarUsuario(scanner, usuarioController);
                    break;

                case 2:
                    iniciarSesion(scanner, usuarioController);
                    break;

                case 3:
                    if (usuarioController.getUsuario() == null) {
                        System.out.println("Debe iniciar sesión primero.");
                    } else {
                        menuUsuarioLogueado(scanner, usuarioController, partidoController);
                    }
                    break;

                case 0:
                    salir = true;
                    System.out.println("¡Gracias por usar el sistema de gestión de partidos!");
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
    private static void inicializarDatos() {
        crearDeportesDisponibles();

        Firebase firebase = new Firebase();
        Mail mail = new Mail();
        INotificacionAdapter firebaseAdapter = new FirebaseAdapter(firebase);
        INotificacionAdapter mailAdapter = new MailAdapter(mail);

        Notificador notificadorPush = new Notificador(firebaseAdapter);
        Notificador notificadorMail = new Notificador(mailAdapter);
        notificadores.add(notificadorPush);
        notificadores.add(notificadorMail);
    }

    private static void crearDeportesDisponibles() {
        // Acá, crea los deportes disponibles en el sistema, para que los usuarios los puedan seleccionar.
        List<Deporte> listaDeportes = new ArrayList<>();
        listaDeportes.add(new Deporte("Fútbol", 5, 11));
        listaDeportes.add(new Deporte("Básquet", 5, 5));
        listaDeportes.add(new Deporte("Vóley", 6, 6));
        listaDeportes.add(new Deporte("Tenis", 2, 4));
        listaDeportes.add(new Deporte("Pádel", 2, 4));

        for (Deporte deporte : listaDeportes) {
            mapDeportes.put(deporte.getNombre(), deporte);
        }
    }


    // Metodo para mostrar el menu.
    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== SISTEMA DE GESTIÓN DE PARTIDOS DEPORTIVOS ===");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Acceder a funcionalidades (requiere inicio de sesión)");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // Metodo para mostrar el menu cuando se loggea el usuario
    private static void menuUsuarioLogueado(Scanner scanner, UsuarioController usuarioController,
                                            PartidoController partidoController) {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n=== MENÚ USUARIO: " + usuarioController.getUsuario().getNombreUsuario() + " ===");
            System.out.println("1. Buscar partidos disponibles por ubicación");
            System.out.println("2. Crear un nuevo partido");
            System.out.println("3. Unirse a un partido");
            System.out.println("4. Gestionar mis partidos");
            System.out.println("5. Actualizar mi perfil");
            System.out.println("6. Ver mis notificaciones");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = obtenerOpcionUsuario(scanner);
            switch (opcion) {
                case 1:
                    buscarPartidosPorUbicacion(scanner, partidoController);
                    break;

                case 2:
                    crearPartido(scanner, partidoController, usuarioController.getUsuario());
                    break;

                case 3:
                    unirseAPartido(scanner, partidoController, usuarioController.getUsuario());
                    break;

                case 4:
                    gestionarMisPartidos(scanner, partidoController, usuarioController.getUsuario());
                    break;

                case 5:
                    actualizarPerfil(scanner, usuarioController);
                    break;

                case 6:
                    System.out.println("*** Simulación de notificaciones ***");
                    simularNotificaciones(usuarioController.getUsuario());
                    break;

                case 0:
                    usuarioController.cerrarSesion();
                    volver = true;
                    break;

                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private static void registrarUsuario(Scanner scanner, UsuarioController usuarioController) {
        System.out.println("\n=== REGISTRO DE USUARIO ===");
        System.out.print("Ingrese nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();

        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setNombreUsuario(nombreUsuario);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setPassword(password);

        System.out.println("\n¿Desea agregar su ubicación? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            UbicacionDTO ubicacion = solicitarUbicacion(scanner);
            nuevoUsuario.setUbicacion(ubicacion);
        }

        System.out.println("\n¿Desea agregar un deporte favorito? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            List<UsuarioDeporteDTO> deportes = new ArrayList<>();

            System.out.print("Nombre del deporte: ");
            String nombreDeporte = scanner.nextLine();

            System.out.println("Nivel (1: PRINCIPIANTE, 2: INTERMEDIO, 3: AVANZADO): ");
            int nivelSeleccionado = Integer.parseInt(scanner.nextLine());
            Nivel nivel = Nivel.PRINCIPIANTE;
            switch (nivelSeleccionado) {
                case 1: nivel = Nivel.PRINCIPIANTE; break;
                case 2: nivel = Nivel.INTERMEDIO; break;
                case 3: nivel = Nivel.AVANZADO; break;
            }

            DeporteDTO deporteDTO = new DeporteDTO();
            deporteDTO.setNombre(nombreDeporte);
            deporteDTO.setJugadoresMinimos(2); // Valores por defecto, ponele que mínimo 2 jugadores.
            deporteDTO.setJugadoresMaximos(22); // Valores por defecto, ponele que máximo 22 jugadores (Futbol 11).

            UsuarioDeporteDTO usuarioDeporteDTO = new UsuarioDeporteDTO();
            usuarioDeporteDTO.setDeporte(deporteDTO);
            usuarioDeporteDTO.setNivel(nivel);

            deportes.add(usuarioDeporteDTO);
            nuevoUsuario.setDeportes(deportes);
        }

        UsuarioDTO resultado = usuarioController.crearUsuario(nuevoUsuario);
        if (resultado != null) {
            System.out.println("\n¡Usuario registrado con éxito!");
        } else {
            System.out.println("\nError al registrar usuario. Intente con otro nombre o correo.");
        }
    }

    private static void iniciarSesion(Scanner scanner, UsuarioController usuarioController) {
        System.out.println("\n=== INICIAR SESIÓN ===");
        System.out.print("Ingrese nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();

        UsuarioDTO resultado = usuarioController.iniciarSesion(nombreUsuario, password);
        if (resultado != null) {
            System.out.println("\n¡Sesión iniciada correctamente! Bienvenido/a " + nombreUsuario);
        } else {
            System.out.println("\nNombre de usuario o contraseña incorrectos.");
        }
    }

    private static void buscarPartidosPorUbicacion(Scanner scanner, PartidoController partidoController) {
        System.out.println("\n=== BUSCAR PARTIDOS POR UBICACIÓN ===");
        UbicacionDTO ubicacion = solicitarUbicacion(scanner);

        System.out.print("Ingrese la distancia máxima en kilómetros: ");
        double distanciaMaxima = Double.parseDouble(scanner.nextLine());

        List<PartidoDTO> partidos = partidoController.buscarPartidosPorUbicacion(ubicacion, distanciaMaxima);

        if (partidos.isEmpty()) {
            System.out.println("No se encontraron partidos en esa ubicación.");
        } else {
            System.out.println("\nPartidos encontrados:");
            mostrarListaPartidos(partidos);
        }
    }

    private static void crearPartido(Scanner scanner, PartidoController partidoController, Usuario organizador) {
        System.out.println("\n=== CREAR NUEVO PARTIDO ===");
        Deporte deporteSeleccionado = null;
        System.out.println("Deportes disponibles:");
        int i = 1;
        for (Deporte deporte : mapDeportes.values()) {
            System.out.println(i + ". " + deporte.getNombre());
            i++;
        }

        System.out.print("Seleccione un deporte (número): ");
        int indiceDeporte = Integer.parseInt(scanner.nextLine());

        if (indiceDeporte > 0 && indiceDeporte <= mapDeportes.size()) {
            String[] deportesArray = mapDeportes.keySet().toArray(new String[0]);
            String nombreDeporte = deportesArray[indiceDeporte - 1];
            deporteSeleccionado = mapDeportes.get(nombreDeporte);
        } else {
            System.out.println("Selección inválida. Usando fútbol por defecto.");
            deporteSeleccionado = mapDeportes.get("Fútbol");
        }

        System.out.print("Cantidad de jugadores requeridos: ");
        int cantidadJugadores = Integer.parseInt(scanner.nextLine());

        System.out.print("Duración del partido (en minutos): ");
        int duracion = Integer.parseInt(scanner.nextLine());

        UbicacionDTO ubicacionDTO = solicitarUbicacion(scanner);

        System.out.println("Fecha del partido (formato dd/MM/yyyy): ");
        String fecha = scanner.nextLine();
        System.out.println("Hora del partido (formato HH:mm): ");
        String hora = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime fechaHora = LocalDateTime.parse(fecha + " " + hora, formatter);
        System.out.println("Seleccione estrategia de emparejamiento:");
        System.out.println("1. Por nivel de habilidad");
        System.out.println("2. Por ubicación/cercanía");
        System.out.println("3. Por historial de partidos");
        int estrategiaSeleccionada = Integer.parseInt(scanner.nextLine());

        String estrategia;
        switch (estrategiaSeleccionada) {
            case 2:
                estrategia = "ubicacion";
                break;
            case 3:
                estrategia = "historial";
                break;
            default:
                estrategia = "nivel";
        }

        PartidoDTO nuevoPartido = partidoController.crearPartido(
            deporteSeleccionado.getNombre(),
            cantidadJugadores,
            duracion,
            ubicacionDTO,
            organizador,
            estrategia,
            fechaHora
        );

        if (nuevoPartido != null) {
            System.out.println("\n¡Partido creado con éxito!");
            System.out.println("ID del partido: " + nuevoPartido.getId());
            System.out.println("Estado inicial: " + nuevoPartido.getEstado());
        } else {
            System.out.println("\nError al crear el partido.");
        }
    }

    private static void unirseAPartido(Scanner scanner, PartidoController partidoController, Usuario usuario) {
        System.out.println("\n=== UNIRSE A UN PARTIDO ===");

        List<PartidoDTO> partidos = partidoController.obtenerTodosLosPartidos();
        if (partidos.isEmpty()) {
            System.out.println("No hay partidos disponibles.");
            return;
        }

        System.out.println("Partidos disponibles:");
        mostrarListaPartidos(partidos);

        System.out.print("Ingrese el ID del partido al que desea unirse: ");
        String idPartido = scanner.nextLine();

        PartidoDTO resultado = partidoController.agregarJugadorAPartido(idPartido, usuario);
        if (resultado != null) {
            System.out.println("\n¡Te has unido al partido con éxito!");
            System.out.println("Estado actual del partido: " + resultado.getEstado());
        } else {
            System.out.println("\nNo pudiste unirte al partido. Puede que el partido no exista o no seas compatible según la estrategia de emparejamiento.");
        }
    }

    private static void gestionarMisPartidos(Scanner scanner, PartidoController partidoController, Usuario usuario) {
        System.out.println("\n=== GESTIONAR MIS PARTIDOS ===");

        // Por simplicidad, mostramos todos los partidos, aunque en realidad, se deberia ver unicamente los partidos de dicho usuario.
        List<PartidoDTO> partidos = partidoController.obtenerTodosLosPartidos();
        if (partidos.isEmpty()) {
            System.out.println("No hay partidos para gestionar.");
            return;
        }

        System.out.println("Mis partidos:");
        mostrarListaPartidos(partidos);

        System.out.print("Ingrese el ID del partido que desea gestionar: ");
        String idPartido = scanner.nextLine();

        System.out.println("¿Qué acción desea realizar?");
        System.out.println("1. Confirmar partido");
        System.out.println("2. Cancelar partido");
        System.out.println("3. Verificar estado actual");
        int accion = Integer.parseInt(scanner.nextLine());

        PartidoDTO resultado = null;
        switch (accion) {
            case 1:
                resultado = partidoController.confirmarPartido(idPartido);
                if (resultado != null) {
                    System.out.println("Partido confirmado. Estado actual: " + resultado.getEstado());
                }
                break;
            case 2:
                resultado = partidoController.cancelarPartido(idPartido);
                if (resultado != null) {
                    System.out.println("Partido cancelado. Estado actual: " + resultado.getEstado());
                }
                break;
            case 3:
                partidoController.verificarEstadosPartidos();
                System.out.println("Estados de partidos actualizados según la fecha/hora actual.");
                break;
            default:
                System.out.println("Opción inválida.");
        }

        if (resultado == null && (accion == 1 || accion == 2)) {
            System.out.println("No se pudo realizar la acción. El partido puede no existir o no tener el estado adecuado.");
        }
    }

    private static void actualizarPerfil(Scanner scanner, UsuarioController usuarioController) {
        System.out.println("\n=== ACTUALIZAR PERFIL ===");

        UsuarioDTO datosActualizados = new UsuarioDTO();

        System.out.println("¿Desea actualizar su ubicación? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            UbicacionDTO ubicacion = solicitarUbicacion(scanner);
            datosActualizados.setUbicacion(ubicacion);
        }

        System.out.println("¿Desea actualizar su contraseña? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            System.out.print("Ingrese nueva contraseña: ");
            String nuevaPassword = scanner.nextLine();
            datosActualizados.setPassword(nuevaPassword);
        }

        UsuarioDTO resultado = usuarioController.actualizarPerfil(datosActualizados);
        if (resultado != null) {
            System.out.println("Perfil actualizado con éxito.");
        } else {
            System.out.println("Error al actualizar perfil.");
        }
    }

    private static void simularNotificaciones(Usuario usuario) {
        System.out.println("\n=== NOTIFICACIONES RECIENTES ===");

        Firebase firebase = new Firebase();
        INotificacionAdapter firebaseAdapter = new FirebaseAdapter(firebase);
        Notificador notificadorPush = new Notificador(firebaseAdapter);

        Mail mail = new Mail();
        INotificacionAdapter mailAdapter = new MailAdapter(mail);
        Notificador notificadorMail = new Notificador(mailAdapter);

        System.out.println("Notificación por Firebase Push:");
        notificadorPush.notificar(usuario, "¡Se ha creado un nuevo partido de tu deporte favorito!");

        System.out.println("\nNotificación por Email:");
        notificadorMail.notificar(usuario, "¡Se han unido suficientes jugadores a tu partido! Estado: Partido Armado");

        System.out.println("\nUsando ambos notificadores para un cambio de estado:");
        String mensaje = "Tu partido ha sido confirmado y está listo para jugarse.";
        notificadorPush.notificar(usuario, mensaje);
        notificadorMail.notificar(usuario, mensaje);
    }

    private static void mostrarListaPartidos(List<PartidoDTO> partidos) {
        for (PartidoDTO partido : partidos) {
            System.out.println("----------------------------------");
            System.out.println("ID: " + partido.getId());
            System.out.println("Deporte: " + partido.getNombreDeporte());
            System.out.println("Estado: " + partido.getEstado());
            System.out.println("Jugadores: " + partido.getCantidadJugadoresAnotados() +
                              "/" + partido.getCantidadJugadoresRequeridos());
            System.out.println("Fecha y hora: " + partido.getFechaHora());
            if (partido.getUbicacion() != null) {
                System.out.println("Ubicación: " + partido.getUbicacion().getDireccion() +
                                  ", " + partido.getUbicacion().getCiudad());
            }
        }
    }

    private static int obtenerOpcionUsuario(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Aca cae, si el usuario ingresa una opicion no valida, como letras o caracteres raros.
        }
    }

    private static UbicacionDTO solicitarUbicacion(Scanner scanner) {
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        System.out.print("Latitud (número decimal): ");
        double latitud = Double.parseDouble(scanner.nextLine());
        System.out.print("Longitud (número decimal): ");
        double longitud = Double.parseDouble(scanner.nextLine());

        return new UbicacionDTO(latitud, longitud, direccion, ciudad);
    }
}
