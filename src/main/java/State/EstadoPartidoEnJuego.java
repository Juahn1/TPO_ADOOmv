package State;

import Model.Partido;
import Model.Usuario;
import java.time.LocalDateTime;

public class EstadoPartidoEnJuego implements EstadoPartido {
    private Partido partido;

    @Override
    public void setContexto(Partido partido) {
        this.partido = partido;
    }

    @Override
    public void agregarJugador(Usuario jugador) {
        System.out.println("No se pueden agregar jugadores a un partido en juego.");
    }

    @Override
    public void eliminarJugador(Usuario jugador) {
        System.out.println("El partido está en juego, no se pueden eliminar jugadores.");
    }

    @Override
    public void confirmarJugador() {
        System.out.println("El partido está en juego, no se pueden confirmar jugadores.");
    }

    @Override
    public void cancelarPartido() {
        System.out.println("El partido está en juego, no se puede cancelar.");
    }

    public void finalizarPartido() {
        partido.cambiarEstado(new EstadoPartidoFinalizado());
        System.out.println("El partido ha terminado, y ha pasado a estado: Finalizado");

        // Actualizar estadísticas de jugadores
        for (Usuario jugador : partido.getJugadoresAnotados()) {
            jugador.setCantidadPartidosJugados(jugador.getCantidadPartidosJugados() + 1);
        }
    }

    public void verificarFinalizacion(LocalDateTime ahora) {
        if (ahora.isAfter(partido.getFechaHora().plusMinutes(partido.getDuracion()))) {
            finalizarPartido();
        }
    }

    @Override
    public String getNombreEstado() {
        return "Partido en juego";
    }
}