package withoutpattern.facade;

import services.UserService;

import services.BookService;

import services.UserService;

import services.BookService;
import services.LoanService;
import services.NotificationService;

public class WithoutFacade {

    public static void run() {

        System.out.println("\n=== EJEMPLO SIN FACADE ===");

        // Creamos todos los servicios necesarios.
        UserService userService = new UserService();
        BookService bookService = new BookService();
        LoanService loanService = new LoanService();
        NotificationService notificationService =
                new NotificationService();

        // Datos de la solicitud.
        int studentId = 25;
        int bookId = 100;

        System.out.println("\nSolicitando prestamo...");

        /*
         * PROBLEMA DE DISEÑO:
         *
         * El cliente debe conocer todos los servicios
         * y coordinar manualmente las operaciones.
         */

        // PASO 1: Validar al estudiante.
        if (!userService.validateUser(studentId)) {
            System.out.println("Prestamo cancelado.");
            return;
        }

        // PASO 2: Comprobar la disponibilidad del libro.
        if (!bookService.isAvailable(bookId)) {
            System.out.println("Prestamo cancelado.");
            return;
        }

        // PASO 3: Registrar el préstamo.
        loanService.registerLoan(studentId, bookId);

        // PASO 4: Actualizar la disponibilidad.
        bookService.markAsBorrowed(bookId);

        // PASO 5: Notificar al estudiante.
        notificationService.sendConfirmation(studentId, bookId);

        System.out.println("\nPrestamo realizado correctamente.");

        // Mostramos los préstamos registrados.
        loanService.showLoans();
    }
}