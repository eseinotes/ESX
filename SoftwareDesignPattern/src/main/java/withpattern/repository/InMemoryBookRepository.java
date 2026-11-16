package withpattern.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.Book;

public class InMemoryBookRepository implements BookRepository {

    private HashMap<String, Book> books;

    public InMemoryBookRepository() {
        books = new HashMap<>();
    }

    @Override
    public void save(Book book) {
        books.put(book.getIsbn(), book);
    }

    @Override
    public Book findByIsbn(String isbn) {
        return books.get(isbn);
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }
}
