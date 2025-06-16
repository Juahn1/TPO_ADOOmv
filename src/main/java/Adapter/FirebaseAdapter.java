package Adapter;
import Model.Firebase;

public class FirebaseAdapter implements INotificacionAdapter {
    private Firebase firebase;

    public FirebaseAdapter(Firebase firebase) {
        this.firebase = firebase;
    }

    @Override
    public void enviar(String destino, String mensaje) {
        firebase.enviarPush(destino, mensaje);
    }
}
