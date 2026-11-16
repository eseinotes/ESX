package withpattern.facade;

import services.BookService;
import services.LoanService;
import services.NotificationService;
import services.UserService;


/*
 * Implementación del patrón Facade.
 *
 * Proporciona una interfaz sencilla para gestionar
 * las operaciones de préstamo de la biblioteca.
 *
 * El cliente no necesita conocer los servicios internos.
 */
public class LibraryFacade {

    private UserService userService;
    private BookService bookService;
    private LoanService loanService;
    private NotificationService notificationService;

    /*
     * La fachada crea y coordina los servicios
     * necesarios para gestionar los préstamos.
     */
    public LibraryFacade() {

        userService = new UserService();
        bookService = new BookService();
        loanService = new LoanService();
        notificationService = new NotificationService();
    }

    /*
     * Método principal de la fachada.
     *
     * Permite realizar un préstamo mediante
     * una única llamada.
     */
    public boolean borrowBook(int studentId, int bookId) {

        // PASO 1: Validar al estudiante.
        if (!userService.validateUser(studentId)) {
            System.out.println("Préstamo cancelado.");
            return false;
        }

        // PASO 2: Comprobar la disponibilidad del libro.
        if (!bookService.isAvailable(bookId)) {
            System.out.println("Préstamo cancelado.");
            return false;
        }

        // PASO 3: Registrar el préstamo.
        loanService.registerLoan(studentId, bookId);

        // PASO 4: Actualizar la disponibilidad.
        bookService.markAsBorrowed(bookId);

        // PASO 5: Enviar la notificación.
        notificationService.sendConfirmation(studentId, bookId);

        return true;
    }

    /*
     * La fachada también puede ofrecer otras
     * operaciones sencillas al cliente.
     */
    public void showLoans() {
        loanService.showLoans();
    }
}