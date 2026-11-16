package model;

/*
 * Interfaz común que utilizará nuestra aplicación.
 */
public interface BookCatalog {

    BookInfo searchBook(String isbn);
}
