package Controllers;

import Model.*;
import DTO.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;


@Data
public class UsuarioController {

    private Usuario usuario;
    private final List<Usuario> usuarios;

    public UsuarioController(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        if (usuarios.stream().anyMatch(u -> u.getNombreUsuario().equals(usuarioDTO.getNombreUsuario()) ||
                                        u.getCorreo().equals(usuarioDTO.getCorreo()))) {
            System.out.println("El nombre de usuario o correo ya están en uso.");
            return null;
        }

        Usuario nuevoUsuario = new Usuario(
            usuarioDTO.getNombreUsuario(),
            usuarioDTO.getCorreo(),
            usuarioDTO.getPassword()
        );

        if (usuarioDTO.getUbicacion() != null) {
            Ubicacion ubicacion = new Ubicacion(
                usuarioDTO.getUbicacion().getCiudad(),
                usuarioDTO.getUbicacion().getDireccion(),
                usuarioDTO.getUbicacion().getLatitud(),
                usuarioDTO.getUbicacion().getLongitud()
            );
            nuevoUsuario.setUbicacion(ubicacion);
        }

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
        nuevoUsuario.setPartidos(new ArrayList<>());
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario registrado: " + nuevoUsuario.getNombreUsuario());
        this.setUsuario(nuevoUsuario);

        return nuevoUsuario.toDTO();
    }

    public UsuarioDTO iniciarSesion(String nombre, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombre) && u.getPassword().equals(contrasena)) {
                return u.login(u.getNombreUsuario(), u.getPassword());
            }
        }
        System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
        return null;
    }

    public UsuarioDTO cerrarSesion() {
        if (usuario != null && usuario.isSesion()) {
            UsuarioDTO devolver = usuario.logout();
            usuario = null;
            return devolver;
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

    public void agregarUsuarioAlSistema(Usuario usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }
}
