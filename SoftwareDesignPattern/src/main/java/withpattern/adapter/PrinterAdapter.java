package withpattern.adapter;

public class PrinterAdapter implements CardPrinter {

    private LegacyPrinter legacyPrinter;
    private ModernPrinter modernPrinter;

    public PrinterAdapter() {
        legacyPrinter = new LegacyPrinter();
        modernPrinter = new ModernPrinter();
    }

    @Override
    public void printCard(String identifier) {

        try {
            // Intentamos convertir el identificador a int.
            int code = Integer.parseInt(identifier);

            // Si podemos convertirlo, utilizamos la impresora antigua.
            legacyPrinter.printCard(code);

        } catch (NumberFormatException exception) {

            // Si no podemos convertirlo, lo tratamos como un DNI.
            modernPrinter.printCard(identifier);
        }
    }
}