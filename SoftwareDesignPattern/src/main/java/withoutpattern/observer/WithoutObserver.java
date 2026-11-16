package withoutpattern.observer;

public class WithoutObserver {

    public static void run() {

        Student ana = new Student("Ana");
        Student carlos = new Student("Carlos");

        Library library = new Library(ana, carlos);

        library.returnBook("Clean Code");
    }
}
