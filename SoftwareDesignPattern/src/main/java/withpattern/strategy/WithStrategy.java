package withpattern.strategy;

import java.util.ArrayList;
import model.LibraryBook;
import model.StrategyBooks;

public class WithStrategy {

    public static void run() {

        // Inicialmente buscamos por título.
        Library library = new Library(
                StrategyBooks.create(),
                new SearchByTitle()
        );

        System.out.println("\nCON STRATEGY: búsqueda con estrategias intercambiables");

        showResults("Por título: Clean",
                library.searchBook("Clean"));

        // Cambiamos la estrategia para buscar por autor.
        library.setStrategy(new SearchByAuthor());

        showResults("Por autor: Robert Martin",
                library.searchBook("Robert Martin"));

        // Cambiamos la estrategia para buscar por año.
        library.setStrategy(new SearchByYear());

        showResults("Por año: 1994",
                library.searchBook("1994"));
    }

    private static void showResults(String search, ArrayList<LibraryBook> books) {

        System.out.println(search);

        for (LibraryBook book : books) {
            System.out.println("  " + book);
        }
    }
}
