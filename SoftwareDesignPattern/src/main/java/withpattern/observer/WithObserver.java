package withpattern.observer;

public class WithObserver {

    public static void run() {

        Library library = new Library();

        Student ana = new Student("Ana");
        Student carlos = new Student("Carlos");
        Student lucia = new Student("Lucía");

        // Ana y Carlos se suscriben.
        library.subscribe(ana);
        library.subscribe(carlos);

        // Primera devolución.
        library.returnBook("Clean Code");

        System.out.println();

        // Carlos deja de recibir notificaciones.
        library.unsubscribe(carlos);

        // Lucía se suscribe.
        library.subscribe(lucia);

        // Segunda devolución.
        library.returnBook("Design Patterns");
    }
}

