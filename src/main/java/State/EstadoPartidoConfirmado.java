package State;

import Model.Partido;
import Model.Usuario;
import java.time.LocalDateTime;

public class EstadoPartidoConfirmado implements EstadoPartido {
    private Partido partido;

    @Override
    public void setContexto(Partido partido) {
        this.partido = partido;
    }

    @Override
    public void agregarJugador(Usuario jugador) {
        System.out.println("No se pueden agregar más jugadores al partido ya confirmado.");
    }

    @Override
    public void eliminarJugador(Usuario jugador) {
        if (esOrganizador(jugador)) {
            System.out.println("El organizador no puede abandonar el partido confirmado.");
            return;
        }

        System.out.println("Jugador eliminado del partido confirmado.");
        partido.getJugadoresAnotados().remove(jugador);
        partido.cambiarEstado(new EstadoPartidoArmado());
        System.out.println("El partido ha vuelto al estado: Armado");
    }

    @Override
    public void confirmarJugador() {
        System.out.println("Todos los jugadores ya están confirmados.");
    }

    @Override
    public void cancelarPartido() {
        System.out.println("Cancelando partido confirmado.");
        partido.cambiarEstado(new EstadoPartidoCancelado());
    }

    public void verificarInicio(LocalDateTime ahora) {
        if (ahora.isAfter(partido.getFechaHora())) {
            partido.cambiarEstado(new EstadoPartidoEnJuego());
            System.out.println("¡El partido ha comenzado! El partido ha pasado a estado: En Juego");
        }
    }

    @Override
    public String getNombreEstado() {
        return "Partido confirmado";
    }

    private boolean esOrganizador(Usuario jugador) {
        return jugador.equals(partido.getOrganizador());
    }
}