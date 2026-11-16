package withpattern.strategy;

import java.util.ArrayList;
import model.LibraryBook;

public interface SearchStrategy {

    ArrayList<LibraryBook> search(ArrayList<LibraryBook> books, String value);
}
