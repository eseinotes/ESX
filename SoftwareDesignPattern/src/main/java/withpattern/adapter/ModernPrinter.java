package withpattern.adapter;

public class ModernPrinter implements CardPrinter {

    @Override
    public void printCard(String dni) {
        System.out.println("Impresora moderna: " + dni);
    }
}