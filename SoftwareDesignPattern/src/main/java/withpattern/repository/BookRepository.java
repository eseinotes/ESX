package withpattern.repository;

import java.util.List;
import model.Book;

public interface BookRepository {

    void save(Book book);

    Book findByIsbn(String isbn);

    List<Book> findAll();
}
