package Model;

public class Firebase {

    // Método original que envía notificaciones a un usuario específico
    public void enviarNotificacion(Usuario usuario) {
        System.out.println("Se notifica al usuario " + usuario.getUsername() + " Felicitaciones!");
    }

    // Método que usa el adaptador para enviar notificaciones push
    public void enviarPush(String destino, String mensaje) {
        System.out.println("Firebase: Enviando notificación push a: " + destino);
        System.out.println("Firebase: Contenido de la notificación: " + mensaje);
    }
}
