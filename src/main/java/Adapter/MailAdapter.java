package Adapter;

import Model.Mail;

public class MailAdapter implements INotificacionAdapter {
    private Mail mailer;

    public MailAdapter(Mail mailer) {
        this.mailer = mailer;
    }

    @Override
    public void enviar(String destinatario, String mensaje) {
        // Delegamos el envío al objeto Mail
        mailer.enviarCorreo(destinatario, mensaje);
    }
}
