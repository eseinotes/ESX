package withoutpattern.decorator;

public class LoanService {

    public void loanBook(String bookTitle,
                         String studentName,
                         boolean notify,
                         boolean log) {

        // Funcionalidad principal.
        System.out.println("Préstamo realizado:");
        System.out.println(studentName + " ha tomado prestado " + bookTitle);

        // Funcionalidad adicional: notificación.
        if (notify) {
            System.out.println("Notificación enviada a " + studentName);
        }

        // Funcionalidad adicional: registro.
        if (log) {
            System.out.println("[LOG] Préstamo registrado: " + bookTitle);
        }
    }
}
