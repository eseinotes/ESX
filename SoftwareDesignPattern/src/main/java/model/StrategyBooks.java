package model;

import java.util.ArrayList;

public class StrategyBooks {

    public static ArrayList<LibraryBook> create() {

        ArrayList<LibraryBook> books = new ArrayList<>();

        books.add(new LibraryBook("Clean Code", "Robert Martin", 2008));
        books.add(new LibraryBook("Design Patterns", "Erich Gamma", 1994));
        books.add(new LibraryBook("Clean Architecture", "Robert Martin", 2017));

        return books;
    }
}
