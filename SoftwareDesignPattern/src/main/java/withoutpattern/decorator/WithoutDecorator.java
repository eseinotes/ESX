package withoutpattern.decorator;

public class WithoutDecorator {

    public static void run() {

        LoanService service = new LoanService();

        System.out.println("PRÉSTAMO BÁSICO");
        service.loanBook("Clean Code", "Ana", false, false);

        System.out.println("\nPRÉSTAMO CON NOTIFICACIÓN");
        service.loanBook("Design Patterns", "Carlos", true, false);

        System.out.println("\nPRÉSTAMO CON NOTIFICACIÓN Y LOG");
        service.loanBook("Clean Architecture", "Lucía", true, true);
    }
}
