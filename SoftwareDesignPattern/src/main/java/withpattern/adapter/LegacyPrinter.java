package withpattern.adapter;

import withoutpattern.adapter.*;

public class LegacyPrinter {

    public void printCard(int studentCode) {
        System.out.println("Imprimiendo carné: " + studentCode);
    }
}