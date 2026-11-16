
package withpattern.repository;

import model.Book;

public class WithRepository {

    public static void run() {

        // Elegimos un repositorio en memoria.
        BookRepository repository = new InMemoryBookRepository();

        // La biblioteca recibe el repositorio.
        Library library = new Library(repository);

        library.addBook(new Book("111", "Clean Code"));
        library.addBook(new Book("222", "Design Patterns"));
        library.addBook(new Book("333", "Clean Architecture"));

        System.out.println("Libro encontrado:");
        System.out.println(library.findBook("222"));

        System.out.println("\nCatálogo de libros:");
        library.showBooks();
    }
}
