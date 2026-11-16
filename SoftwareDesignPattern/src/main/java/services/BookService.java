package services;

/*
 * Servicio encargado de gestionar
 * la disponibilidad de los libros.
 */
public class BookService {

    private boolean available;

    public BookService() {
        available = true;
    }

    // Comprueba si el libro existe y está disponible.
    public boolean isAvailable(int bookId) {

        System.out.println("2. Comprobando disponibilidad del libro...");

        if (bookId != 100 || !available) {
            System.out.println("Libro no disponible.");
            return false;
        }

        System.out.println("Libro disponible.");
        return true;
    }

    // Marca el libro como prestado.
    public void markAsBorrowed(int bookId) {

        if (bookId == 100) {
            available = false;
            System.out.println("Libro marcado como prestado.");
        }
    }
}