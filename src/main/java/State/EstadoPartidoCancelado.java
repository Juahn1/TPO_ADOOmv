package State;

import Model.Partido;
import Model.Usuario;

public class EstadoPartidoCancelado implements EstadoPartido {
    private Partido partido;

    @Override
    public void setContexto(Partido partido) {
        this.partido = partido;
    }

    @Override
    public void agregarJugador(Usuario jugador) {
        System.out.println("No se pueden agregar jugadores a un partido cancelado.");
    }

    @Override
    public void eliminarJugador(Usuario jugador) {
        System.out.println("No se pueden eliminar jugadores de un partido cancelado.");
    }

    @Override
    public void confirmarJugador() {
        System.out.println("No se pueden confirmar jugadores en un partido cancelado.");
    }

    @Override
    public void cancelarPartido() {
        System.out.println("El partido ya está cancelado.");
    }

    @Override
    public String getNombreEstado() {
        return "Partido cancelado";
    }
}