package withoutpattern.singleton;

public class WithoutSingleton {

    public static void run() {

        System.out.println("\n=== EJEMPLO SIN SINGLETON ===");

        // El administrador obtiene su propia configuración.
        LibraryConfig adminConfig = new LibraryConfig();

        // El sistema de préstamos crea otra configuración independiente.
        LibraryConfig loanConfig = new LibraryConfig();

        System.out.println("\nCONFIGURACION INICIAL");

        System.out.println("Límite del administrador: "
                + adminConfig.getMaxBooks());

        System.out.println("Límite del sistema de prestamos: "
                + loanConfig.getMaxBooks());

        // El administrador modifica la política de préstamos.
        System.out.println("\nEl administrador aumenta el límite a 5 libros.");

        adminConfig.setMaxBooks(5);

        System.out.println("\nCONFIGURACION ACTUAL");

        System.out.println("Límite del administrador: "
                + adminConfig.getMaxBooks());

        System.out.println("Límite del sistema de préstamos: "
                + loanConfig.getMaxBooks());

        // Un estudiante tiene actualmente 4 libros prestados.
        int borrowedBooks = 4;

        System.out.println("\nUn estudiante tiene "
                + borrowedBooks + " libros prestados.");

        // El sistema de préstamos utiliza su propia configuración.
        if (loanConfig.canBorrow(borrowedBooks)) {
            System.out.println("Prestamo autorizado.");
        } else {
            System.out.println("Prestamo rechazado.");
        }

        // Comprobamos si las referencias apuntan al mismo objeto.
        System.out.println("\n¿Es la misma configuracion? "
                + (adminConfig == loanConfig));
    }
}