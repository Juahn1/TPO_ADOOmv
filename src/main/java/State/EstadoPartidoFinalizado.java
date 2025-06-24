package State;

import Model.Partido;
import Model.Usuario;

public class EstadoPartidoFinalizado implements EstadoPartido {
    private Partido partido;

    @Override
    public void setContexto(Partido partido) {
        this.partido = partido;
    }

    @Override
    public void agregarJugador(Usuario jugador) {
        System.out.println("No se pueden agregar jugadores a un partido finalizado.");
    }

    @Override
    public void eliminarJugador(Usuario jugador) {
        System.out.println("No se pueden eliminar jugadores de un partido finalizado.");
    }

    @Override
    public void confirmarJugador() {
        System.out.println("No se pueden confirmar jugadores en un partido finalizado.");
    }

    @Override
    public void cancelarPartido() {
        System.out.println("No se puede cancelar un partido finalizado.");
    }

    @Override
    public String getNombreEstado() {
        return "Partido finalizado";
    }
}