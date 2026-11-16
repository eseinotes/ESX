package services;

/*
 * Servicio encargado de enviar notificaciones.
 *
 * El envío es simulado mediante la consola.
 */
public class NotificationService {

    public void sendConfirmation(int studentId, int bookId) {

        System.out.println("4. Enviando notificación...");

        System.out.println("Notificacion enviada al estudiante "
                + studentId + ".");

        System.out.println("Prestamo del libro " + bookId + " confirmado.");
    }
}