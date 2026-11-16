
package withpattern.decorator;

public class LoggingDecorator extends LoanDecorator {

    public LoggingDecorator(LoanService service) {
        super(service);
    }

    @Override
    public void loanBook(String bookTitle, String studentName) {

        // Ejecutamos el comportamiento del objeto decorado.
        super.loanBook(bookTitle, studentName);

        // Añadimos el registro.
        System.out.println("[LOG] Préstamo registrado: " + bookTitle);
    }
}
