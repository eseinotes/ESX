package legacy;

/*
 * Representa un registro de libro del sistema antiguo.
 *
 * Esta clase pertenece al software heredado
 * y no podemos modificarla.
 */
public class LegacyBookRecord {

    private int id;
    private String title;
    private String author;

    public LegacyBookRecord(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
