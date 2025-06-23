package main.java.Adapter;
import Model.Mail;

public class MailAdapter implements INotificacionAdapter {
    private Mail mailer;

    public MailAdapter(Mail mailer) {
        this.mailer = mailer;
    }

    @Override
    public void enviar(String destinatario, String mensaje) {
        mailer.enviarCorreo(destinatario, mensaje);
    }
}
