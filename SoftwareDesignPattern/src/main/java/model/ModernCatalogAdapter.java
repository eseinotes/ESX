package model;

/*
 * Adaptador del catálogo moderno.
 *
 * En este caso no necesitamos transformar los datos,
 * porque el catálogo ya trabaja con ISBN y BookInfo.
 */
public class ModernCatalogAdapter implements BookCatalog {

    private ModernCatalog modernCatalog;

    public ModernCatalogAdapter(ModernCatalog modernCatalog) {
        this.modernCatalog = modernCatalog;
    }

    public BookInfo searchBook(String isbn) {
        return modernCatalog.searchBook(isbn);
    }

   
}
