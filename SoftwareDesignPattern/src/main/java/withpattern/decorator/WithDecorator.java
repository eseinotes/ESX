
package withpattern.decorator;

public class WithDecorator {

    public static void run() {

        // Primer ejemplo: préstamo básico.
        LoanService basicLoan = new BasicLoanService();

        System.out.println("PRÉSTAMO BÁSICO");
        basicLoan.loanBook("Clean Code", "Ana");

        // Segundo ejemplo: préstamo con notificación.
        LoanService notificationLoan =
                new NotificationDecorator(new BasicLoanService());

        System.out.println("\nPRÉSTAMO CON NOTIFICACIÓN");
        notificationLoan.loanBook("Design Patterns", "Carlos");

        // Tercer ejemplo: préstamo con notificación y log.
        LoanService completeLoan =
                new LoggingDecorator(
                        new NotificationDecorator(
                                new BasicLoanService()
                        )
                );

        System.out.println("\nPRÉSTAMO CON NOTIFICACIÓN Y LOG");
        completeLoan.loanBook("Clean Architecture", "Lucía");
    }
}
