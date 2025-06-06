package Model;

public class MailAdapter implements INotificacionAdapter {
    private Mail mail;

    public MailAdapter(Mail mail) {
        this.mail = mail;
    }

    @Override
    public void enviar(String destino, String mensaje) {
        mail.enviarCorreo(destino, mensaje);
    }
}
