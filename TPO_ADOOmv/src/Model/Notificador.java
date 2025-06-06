package Model;

public class Notificador {
    private INotificacionAdapter adapter;

    public Notificador(INotificacionAdapter adapter) {
        this.adapter = adapter;
    }

    public void notificar(Usuario usuario, String mensaje) {
        adapter.enviar(usuario.getIdentificador(), mensaje);
    }
}
