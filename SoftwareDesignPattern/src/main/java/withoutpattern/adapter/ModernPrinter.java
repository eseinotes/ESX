package withoutpattern.adapter;

public class ModernPrinter implements CardPrinter {

    @Override
    public void printCard(String studentCode) {
        System.out.println("Impresora moderna: " + studentCode);
    }
}
