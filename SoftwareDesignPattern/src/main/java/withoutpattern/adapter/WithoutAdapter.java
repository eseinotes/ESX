package withoutpattern.adapter;

public class WithoutAdapter {

    public static void run() {

        System.out.println("\n=== EJEMPLO SIN ADAPTER ===");

        // Creamos las dos impresoras de la biblioteca.
        LegacyPrinter legacyPrinter = new LegacyPrinter();
        ModernPrinter modernPrinter = new ModernPrinter();

        // Identificadores de dos estudiantes.
        String student1 = "1234";
        String student2 = "12345678Z";

        // PRIMER ESTUDIANTE: sistema antiguo.
        System.out.println("\nESTUDIANTE 1");

        try {
            // Intentamos convertir el identificador a entero.
            int code = Integer.parseInt(student1);

            // Si es numérico, utilizamos la impresora antigua.
            legacyPrinter.printCard(code);

        } catch (NumberFormatException exception) {

            // Si no es numérico, utilizamos la impresora moderna.
            modernPrinter.printCard(student1);
        }

        // SEGUNDO ESTUDIANTE: sistema moderno.
        System.out.println("\nESTUDIANTE 2");

        try {
            // Repetimos exactamente la misma lógica.
            int code = Integer.parseInt(student2);

            legacyPrinter.printCard(code);

        } catch (NumberFormatException exception) {

            modernPrinter.printCard(student2);
        }
    }
}
