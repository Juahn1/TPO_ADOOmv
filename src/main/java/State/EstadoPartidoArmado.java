package State;

import Model.Partido;
import Model.Usuario;

public class EstadoPartidoArmado implements EstadoPartido {
    private Partido partido;

    @Override
    public void setContexto(Partido partido) {
        this.partido = partido;
    }

    @Override
    public void agregarJugador(Usuario jugador) {
        System.out.println("No se puede agregar más jugadores al partido armado. Ya se completó el cupo necesario.");
    }

    @Override
    public void eliminarJugador(Usuario jugador) {
        if (esOrganizador(jugador)) {
            System.out.println("El organizador no puede abandonar el partido sin cancelarlo.");
            return;
        }

        System.out.println("Jugador " + jugador.getNombreUsuario() + " eliminado del partido armado.");
        partido.getJugadoresAnotados().remove(jugador);
        if (partido.getJugadoresAnotados().size() < partido.getCantidadJugadoresRequeridos()) {
            partido.cambiarEstado(new EstadoBuscandoJugadores());
            System.out.println("Se bajó alguien, el partido ahora volvió a estado: Buscando Jugadores");
        }
    }

    @Override
    public void confirmarJugador() {
        // Cuando todos los jugadores han confirmado en Partido.verificarConfirmaciones()
        // este método es llamado para efectuar el cambio de estado
        System.out.println("Todos los jugadores han confirmado su participación.");
        partido.cambiarEstado(new EstadoPartidoConfirmado());
        System.out.println("¡El partido ha sido confirmado y pasa a estado: Confirmado!");
    }

    @Override
    public void cancelarPartido() {
        System.out.println("Cancelando partido armado.");
        partido.cambiarEstado(new EstadoPartidoCancelado());
        System.out.println("Partido cancelado exitosamente.");
    }

    @Override
    public String getNombreEstado() {
        return "Partido armado";
    }

    private boolean esOrganizador(Usuario jugador) {
        return jugador.equals(partido.getOrganizador());
    }
}