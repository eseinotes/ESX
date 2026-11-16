package model;


/*
 * Catálogo moderno.
 *
 * Los libros se identifican mediante su ISBN.
 */
public class ModernCatalog {

    public BookInfo searchBook(String isbn) {

        if (isbn.equals("12341")) {
            return new BookInfo(
                    isbn,
                    "Effective Java",
                    "Joshua Bloch"
            );
        }

        return null;
    }
}