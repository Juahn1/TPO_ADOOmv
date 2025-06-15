package Model;

public class EstadoBuscandoJugadores extends EstadoPartido {

    @Override
    public void agregarJugador(Partido contexto) {
        System.out.println("Jugador agregado a partido que busca jugadores.");
        if (contexto.getJugadoresAnotados().size() >= contexto.getCantidadJugadoresRequeridos()) {
            contexto.cambiarEstado(new EstadoPartidoArmado());
            System.out.println("El partido completó sus jugadores y pasa a partido armado!");
        }
    }

    @Override
    public void eliminarJugador(Partido contexto) {
        System.out.println("Jugador eliminado del partido en búsqueda.");
    }

    @Override
    public void confirmarJugador(Partido contexto) {
        System.out.println("El jugador ha confirmado pero el partido aún está en búsqueda de jugadores.");
    }

    @Override
    public void cancelarPartido() {
        System.out.println("Partido que buscaba jugadores, fue cancelado");
    }
}