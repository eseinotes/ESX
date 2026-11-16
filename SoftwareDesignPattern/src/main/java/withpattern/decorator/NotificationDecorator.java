
package withpattern.decorator;

public class NotificationDecorator extends LoanDecorator {

    public NotificationDecorator(LoanService service) {
        super(service);
    }

    @Override
    public void loanBook(String bookTitle, String studentName) {

        // Ejecutamos primero el comportamiento original.
        super.loanBook(bookTitle, studentName);

        // Añadimos la nueva funcionalidad.
        System.out.println("Notificación enviada a " + studentName);
    }
}
