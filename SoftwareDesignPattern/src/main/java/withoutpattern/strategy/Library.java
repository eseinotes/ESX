package withoutpattern.strategy;

import java.util.ArrayList;
import model.LibraryBook;

public class Library {

    private ArrayList<LibraryBook> books;

    public Library(ArrayList<LibraryBook> books) {
        this.books = books;
    }

    public ArrayList<LibraryBook> searchBook(String searchType, String value) {

        ArrayList<LibraryBook> results = new ArrayList<>();

        // Cada nueva forma de buscar exige modificar este método.
        for (LibraryBook book : books) {

            if (searchType.equals("TITLE")) {

                if (book.getTitle().toLowerCase().contains(value.toLowerCase())) {
                    results.add(book);
                }

            } else if (searchType.equals("AUTHOR")) {

                if (book.getAuthor().toLowerCase().contains(value.toLowerCase())) {
                    results.add(book);
                }

            } else if (searchType.equals("YEAR")) {

                if (book.getYear() == Integer.parseInt(value)) {
                    results.add(book);
                }
            }
        }

        return results;
    }
}
