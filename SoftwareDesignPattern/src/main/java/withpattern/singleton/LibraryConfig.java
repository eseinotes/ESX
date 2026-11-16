package withpattern.singleton;

/*
 * Implementación del patrón Singleton.
 *
 * Garantiza que todos los componentes de la aplicación
 * utilicen una misma instancia de configuración.
 */
public class LibraryConfig {

    // Única instancia de la clase.
    private static final LibraryConfig instance = new LibraryConfig();

    // Configuración compartida.
    private int maxBooks;

    /*
     * Constructor privado.
     *
     * Impide crear objetos desde otras clases mediante new.
     */
    private LibraryConfig() {
        maxBooks = 3;
    }

    /*
     * Método público y estático.
     *
     * Siempre devuelve la misma instancia.
     */
    public static LibraryConfig getInstance() {
        return instance;
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
