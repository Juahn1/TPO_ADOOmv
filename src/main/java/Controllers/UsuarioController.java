package Controllers;

import Model.*;
import DTO.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioController {

    private Usuario usuario; // Usuario actualmente logueado
    private final List<Usuario> usuarios; // Lista de usuarios recibida desde el Main

    public UsuarioController(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        // Verificar si el usuario ya existe
        if (usuarios.stream().anyMatch(u -> u.getNombreUsuario().equals(usuarioDTO.getNombreUsuario()) ||
                                        u.getCorreo().equals(usuarioDTO.getCorreo()))) {
            System.out.println("El nombre de usuario o correo ya están en uso.");
            return null;
        }

        // Crear el nuevo usuario
        Usuario nuevoUsuario = new Usuario(
            usuarioDTO.getNombreUsuario(),
            usuarioDTO.getCorreo(),
            usuarioDTO.getPassword()
        );

        // Añadir ubicación si existe
        if (usuarioDTO.getUbicacion() != null) {
            Ubicacion ubicacion = new Ubicacion(
                usuarioDTO.getUbicacion().getCiudad(),
                usuarioDTO.getUbicacion().getDireccion(),
                usuarioDTO.getUbicacion().getLatitud(),
                usuarioDTO.getUbicacion().getLongitud()
            );
            nuevoUsuario.setUbicacion(ubicacion);
        }

        // Añadir deportes si existen
        if (usuarioDTO.getDeportes() != null) {
            List<UsuarioDeporte> listaDeportes = new ArrayList<>();
            for (UsuarioDeporteDTO dto : usuarioDTO.getDeportes()) {
                UsuarioDeporte ud = new UsuarioDeporte();
                ud.setNivel(dto.getNivel());

                Deporte d = new Deporte(
                    dto.getDeporte().getNombre(),
                    dto.getDeporte().getJugadoresMinimos(),
                    dto.getDeporte().getJugadoresMaximos()
                );

                ud.setDeporte(d);
                ud.setJugador(nuevoUsuario);
                listaDeportes.add(ud);
            }
            nuevoUsuario.setDeportes(listaDeportes);
        }

        // Inicializar la lista de partidos vacía
        nuevoUsuario.setPartidos(new ArrayList<>());

        // Añadir usuario a la lista
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario registrado: " + nuevoUsuario.getNombreUsuario());

        // Establecer este usuario como el usuario activo (opcional)
        this.setUsuario(nuevoUsuario);

        return nuevoUsuario.toDTO();
    }

    public UsuarioDTO iniciarSesion(String nombre, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombre) && u.getPassword().equals(contrasena)) {
                // La validación la hace el controlador, no el modelo
                u.setSesion(true);
                this.setUsuario(u);
                System.out.println("Login exitoso para el usuario: " + nombre);
                return u.toDTO();
            }
        }
        System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
        return null;
    }

    public UsuarioDTO cerrarSesion() {
        if (usuario != null && usuario.isSesion()) {
            // La lógica de cerrar sesión la maneja el controlador
            usuario.setSesion(false);
            System.out.println("Sesión cerrada para: " + usuario.getNombreUsuario());
            UsuarioDTO dto = usuario.toDTO();
            this.usuario = null;
            return dto;
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

    public List<PartidoDTO> buscarPartidosCercanos(UbicacionDTO ubicacionDTO, double distanciaMaxima) {
        // Este método requeriría acceso al PartidoController
        // Por ahora devolvemos una lista vacía
        return new ArrayList<>();
    }

    public UsuarioDTO actualizarPerfil(UsuarioDTO datosActualizados) {
        if (usuario != null) {
            if (datosActualizados.getUbicacion() != null) {
                Ubicacion ubicacion = new Ubicacion(
                    datosActualizados.getUbicacion().getCiudad(),
                    datosActualizados.getUbicacion().getDireccion(),
                    datosActualizados.getUbicacion().getLatitud(),
                    datosActualizados.getUbicacion().getLongitud()
                );
                usuario.setUbicacion(ubicacion);
            }

            if (datosActualizados.getPassword() != null && !datosActualizados.getPassword().isEmpty()) {
                usuario.setPassword(datosActualizados.getPassword());
            }

            System.out.println("Perfil actualizado para: " + usuario.getNombreUsuario());
            return usuario.toDTO();
        }
        System.out.println("No hay usuario logueado para actualizar.");
        return null;
    }

    public UsuarioDTO getUsuarioActual() {
        return usuario != null ? usuario.toDTO() : null;
    }

    // Método para pruebas o precarga de usuarios
    public void agregarUsuarioAlSistema(Usuario usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }
}
