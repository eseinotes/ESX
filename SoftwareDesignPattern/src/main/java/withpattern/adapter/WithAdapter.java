package withpattern.adapter;


public class WithAdapter {

    public static void run() {

        CardPrinter printer = new PrinterAdapter();

        // Estudiante del sistema antiguo.
        printer.printCard("1234");

        // Estudiante del sistema moderno.
        printer.printCard("12345678Z");
    }
}