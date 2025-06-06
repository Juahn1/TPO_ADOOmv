package Model;

public class Firebase {
    public void enviarPush(String idUsuario, String texto) {
        System.out.println("Push enviado a " + idUsuario + ": " + texto);
    }
}
