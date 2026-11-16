package withoutpattern.repository;

import java.util.HashMap;
import model.Book;
import model.Book;

public class Library {

    // La biblioteca almacena directamente los libros.
    private HashMap<String, Book> books;

    public Library() {
        books = new HashMap<>();
    }

    // Añade un libro al HashMap.
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    // Busca un libro directamente en el HashMap.
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    // Muestra todos los libros.
    public void showBooks() {

        for (Book book : books.values()) {
            System.out.println(book);
        }
    }
}
