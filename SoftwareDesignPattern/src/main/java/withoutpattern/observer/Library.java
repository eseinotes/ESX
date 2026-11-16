package withoutpattern.observer;

public class Library {

    private Student student1;
    private Student student2;

    public Library(Student student1, Student student2) {
        this.student1 = student1;
        this.student2 = student2;
    }

    public void returnBook(String bookTitle) {

        System.out.println("Libro devuelto: " + bookTitle);

        // La biblioteca debe notificar directamente
        // a cada estudiante.

        student1.receiveNotification(
                "El libro " + bookTitle + " está disponible."
        );

        student2.receiveNotification(
                "El libro " + bookTitle + " está disponible."
        );
    }
}
