package withpattern.repository;

import java.util.ArrayList;
import java.util.List;
import model.Book;

public class MySQLBookRepository implements BookRepository {

    @Override
    public void save(Book book) {
        // INSERT o UPDATE en MySQL.
    }

    @Override
    public Book findByIsbn(String isbn) {
        // SELECT por ISBN.
        return null;
    }

    @Override
    public List<Book> findAll() {
        // SELECT de todos los libros.
        return new ArrayList<>();
    }
}