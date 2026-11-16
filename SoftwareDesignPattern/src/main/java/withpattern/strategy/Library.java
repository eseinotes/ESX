package withpattern.strategy;

import java.util.ArrayList;
import model.LibraryBook;

public class Library {

    private ArrayList<LibraryBook> books;
    private SearchStrategy strategy;

    public Library(ArrayList<LibraryBook> books, SearchStrategy strategy) {
        this.books = books;
        this.strategy = strategy;
    }

    // Permite cambiar el algoritmo de búsqueda.
    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    // La biblioteca delega la búsqueda en la estrategia.
    public ArrayList<LibraryBook> searchBook(String value) {
        return strategy.search(books, value);
    }
}
