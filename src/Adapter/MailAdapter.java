package Adapter;

import Model.INotificacionAdapter;

public class MailAdapter implements INotificacionAdapter {

    @Override
    public void enviar(String destinatario, String mensaje) {
        // Simulación de envío de correo
        System.out.println("Enviando correo a: " + destinatario);
        System.out.println("Mensaje: " + mensaje);
    }
}
