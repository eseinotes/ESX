package model;

/*
 * Modelo de libro utilizado por nuestra nueva aplicación.
 */
public class BookInfo {

    private String isbn;
    private String title;
    private String author;

    public BookInfo(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
