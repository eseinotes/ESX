package withpattern.repository;

import model.Book;

public class Library {

    private BookRepository repository;

    public Library(BookRepository repository) {
        this.repository = repository;
    }

    // La biblioteca delega el almacenamiento en el repositorio.
    public void addBook(Book book) {
        repository.save(book);
    }

    // La biblioteca delega la búsqueda en el repositorio.
    public Book findBook(String isbn) {
        return repository.findByIsbn(isbn);
    }

    // La biblioteca obtiene los libros desde el repositorio.
    public void showBooks() {

        for (Book book : repository.findAll()) {
            System.out.println(book);
        }
    }
}
