package withpattern.facade;

public class WithFacade {

    public static void run() {

        System.out.println("\n=== EJEMPLO CON FACADE ===");

        /*
         * Creamos la fachada.
         *
         * No necesitamos crear los servicios
         * individuales desde el cliente.
         */
        LibraryFacade library = new LibraryFacade();

        // Datos de la solicitud.
        int studentId = 25;
        int bookId = 100;

        System.out.println("\nSolicitando prestamo...");

        /*
         * Una única llamada permite realizar
         * todas las operaciones necesarias.
         */
        boolean success = library.borrowBook(studentId, bookId);

        if (success) {
            System.out.println("\nPrestamo realizado correctamente.");
        } else {
            System.out.println("\nNo se ha podido realizar el préstamo.");
        }

        // Consultamos los préstamos.
        library.showLoans();
    }
}