package withpattern.strategy;

import java.util.ArrayList;
import model.LibraryBook;

public class SearchByTitle implements SearchStrategy {

    @Override
    public ArrayList<LibraryBook> search(ArrayList<LibraryBook> books, String value) {

        ArrayList<LibraryBook> results = new ArrayList<>();

        for (LibraryBook book : books) {

            if (book.getTitle().toLowerCase().contains(value.toLowerCase())) {
                results.add(book);
            }
        }

        return results;
    }
}
