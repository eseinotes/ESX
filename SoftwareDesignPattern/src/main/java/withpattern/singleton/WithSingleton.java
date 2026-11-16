package withpattern.singleton;

public class WithSingleton {

    public static void run() {

        System.out.println("\n=== EJEMPLO CON SINGLETON ===");

        /*
         * Restablecemos la configuración inicial.
         *
         * Como la instancia es compartida, sus valores permanecen
         * entre distintas ejecuciones del ejemplo.
         */
        LibraryConfig.getInstance().setMaxBooks(3);

        // El administrador obtiene la configuración.
        LibraryConfig adminConfig = LibraryConfig.getInstance();

        // El sistema de préstamos obtiene la configuración.
        LibraryConfig loanConfig = LibraryConfig.getInstance();

        System.out.println("\nCONFIGURACIÓN INICIAL");

        System.out.println("Límite del administrador: "
                + adminConfig.getMaxBooks());

        System.out.println("Límite del sistema de préstamos: "
                + loanConfig.getMaxBooks());

        // El administrador modifica la política de préstamos.
        System.out.println("\nEl administrador aumenta el límite a 5 libros.");

        adminConfig.setMaxBooks(5);

        System.out.println("\nCONFIGURACIÓN ACTUAL");

        System.out.println("Límite del administrador: "
                + adminConfig.getMaxBooks());

        System.out.println("Límite del sistema de préstamos: "
                + loanConfig.getMaxBooks());

        // Un estudiante tiene actualmente 4 libros prestados.
        int borrowedBooks = 4;

        System.out.println("\nUn estudiante tiene "
                + borrowedBooks + " libros prestados.");

        // El sistema de préstamos consulta la configuración compartida.
        if (loanConfig.canBorrow(borrowedBooks)) {
            System.out.println("Préstamo autorizado.");
        } else {
            System.out.println("Préstamo rechazado.");
        }

        // Comprobamos si las referencias apuntan al mismo objeto.
        System.out.println("\n¿Es la misma configuración? "
                + (adminConfig == loanConfig));
    }
}