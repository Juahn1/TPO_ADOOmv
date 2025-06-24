package State;
import Model.Partido;
import Model.Usuario;

public class EstadoBuscandoJugadores implements EstadoPartido {
    private Partido partido;

    @Override
    public void setContexto(Partido partido) {
        this.partido = partido;
    }

    @Override
    public void agregarJugador(Usuario jugador) {
        System.out.println("Jugador " + jugador.getNombreUsuario() + " agregado a partido que busca jugadores.");
        if (!partido.getJugadoresAnotados().contains(jugador)) {
            partido.getJugadoresAnotados().add(jugador);
            verificarCantidadJugadores();
        }
    }

    @Override
    public void eliminarJugador(Usuario jugador) {
        if (esOrganizador(jugador)) {
            System.out.println("El organizador no puede abandonar el partido sin cancelarlo.");
            return;
        }
        System.out.println("Jugador " + jugador.getNombreUsuario() + " eliminado del partido en búsqueda.");
        partido.getJugadoresAnotados().remove(jugador);
    }

    @Override
    public void confirmarJugador() {
        System.out.println("No se puede confirmar el partido porque aún no se han registrado todos los jugadores necesarios.");
    }

    @Override
    public void cancelarPartido() {
        System.out.println("Cancelando partido en estado de búsqueda de jugadores.");
        partido.cambiarEstado(new EstadoPartidoCancelado());
        System.out.println("Partido cancelado exitosamente.");
    }

    @Override
    public String getNombreEstado() {
        return "Buscando jugadores";
    }

    private void verificarCantidadJugadores() {
        if (partido.getJugadoresAnotados().size() >= partido.getCantidadJugadoresRequeridos()) {
            partido.cambiarEstado(new EstadoPartidoArmado());
            System.out.println("¡El partido completó sus jugadores y pasa a estado: Partido armado!");
        }
    }

    private boolean esOrganizador(Usuario jugador) {
        return jugador.equals(partido.getOrganizador());
    }
}