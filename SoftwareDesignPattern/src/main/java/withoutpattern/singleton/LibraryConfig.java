package withoutpattern.singleton;

/*
 * Representa la configuración de préstamos de una biblioteca.
 *
 * Cada vez que se utiliza new LibraryConfig(), se crea
 * un objeto independiente con su propia configuración.
 */
public class LibraryConfig {

    private int maxBooks;

    public LibraryConfig() {
        maxBooks = 3;
    }

    public int getMaxBooks() {
        return maxBooks;
    }

    public void setMaxBooks(int maxBooks) {
        this.maxBooks = maxBooks;
    }

    // Comprueba si un estudiante puede solicitar otro libro.
    public boolean canBorrow(int currentBooks) {
        return currentBooks < maxBooks;
    }
}