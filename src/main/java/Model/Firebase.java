package Model;

public class Firebase {

    public void enviarNotificacion(Usuario usuario) {
        System.out.println("Se notifica al usuario " + "usuario.getUsername()" + " Felicitaciones!");
    }

    public void enviarPush(String destino, String mensaje) {
        System.out.println("Firebase: Enviando notificación push a: " + destino);
        System.out.println("Firebase: Contenido de la notificación: " + mensaje);
    }
}
