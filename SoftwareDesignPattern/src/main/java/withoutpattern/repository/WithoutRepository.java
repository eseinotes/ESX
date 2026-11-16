package withoutpattern.repository;

import model.Book;

public class WithoutRepository {

    public static void run() {

        Library library = new Library();

        library.addBook(new Book("111", "Clean Code"));
        library.addBook(new Book("222", "Design Patterns"));
        library.addBook(new Book("333", "Clean Architecture"));

        System.out.println("Libro encontrado:");
        System.out.println(library.findBook("222"));

        System.out.println("\nCatálogo de libros:");
        library.showBooks();
    }
}
