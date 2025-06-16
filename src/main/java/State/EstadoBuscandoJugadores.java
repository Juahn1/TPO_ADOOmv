package State;
import Model.Partido;

public class EstadoBuscandoJugadores implements EstadoPartido {

    @Override
    public void agregarJugador(Partido partido) {
        System.out.println("Jugador agregado a partido que busca jugadores.");
        if (partido.getJugadoresAnotados().size() >= partido.getCantidadJugadoresRequeridos()) {
            partido.cambiarEstado(new EstadoPartidoArmado());
            System.out.println("El partido completó sus jugadores y pasa a partido armado!");
            partido.notificar(); // Notificar el cambio de estado
        }
    }

    @Override
    public void eliminarJugador(Partido partido) {
        System.out.println("Jugador eliminado del partido en búsqueda.");
    }

    @Override
    public void confirmarJugador(Partido partido) {
        System.out.println("El jugador ha confirmado pero el partido aún está en búsqueda de jugadores.");
    }

    @Override
    public void cancelarPartido() {
        System.out.println("Partido que buscaba jugadores, fue cancelado");
    }
}