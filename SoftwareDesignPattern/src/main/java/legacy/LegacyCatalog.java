package legacy;

/*
 * Catálogo antiguo de la biblioteca.
 *
 * Los libros se identifican mediante un ID numérico.
 */
public class LegacyCatalog {

    public LegacyBookRecord searchBook(int id) {

        if (id == 101) {
            return new LegacyBookRecord(
                    101,
                    "Clean Code",
                    "Robert C. Martin"
            );
        }

        if (id == 102) {
            return new LegacyBookRecord(
                    102,
                    "Design Patterns",
                    "Erich Gamma y otros"
            );
        }

        return null;
    }
}