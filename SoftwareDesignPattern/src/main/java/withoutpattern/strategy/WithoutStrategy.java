package withoutpattern.strategy;

import java.util.ArrayList;
import model.LibraryBook;
import model.StrategyBooks;

public class WithoutStrategy {

    public static void run() {

        Library library = new Library(StrategyBooks.create());

        System.out.println("\nSIN STRATEGY: búsquedas con if/else en Library");

        showResults("Por título: Clean",
                library.searchBook("TITLE", "Clean"));

        showResults("Por autor: Robert Martin",
                library.searchBook("AUTHOR", "Robert Martin"));

        showResults("Por año: 1994",
                library.searchBook("YEAR", "1994"));
    }

    private static void showResults(String search, ArrayList<LibraryBook> books) {

        System.out.println(search);

        for (LibraryBook book : books) {
            System.out.println("  " + book);
        }
    }
}
