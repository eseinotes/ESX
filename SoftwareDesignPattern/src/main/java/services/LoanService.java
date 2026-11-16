package services;

import java.util.ArrayList;

/*
 * Servicio encargado de registrar los préstamos.
 */
public class LoanService {

    private ArrayList<String> loans;

    public LoanService() {
        loans = new ArrayList<>();
    }

    public void registerLoan(int studentId, int bookId) {

        System.out.println("3. Registrando préstamo...");

        String loan = "Estudiante " + studentId
                + " - Libro " + bookId;

        loans.add(loan);

        System.out.println("Prestamo registrado correctamente.");
    }

    public void showLoans() {

        System.out.println("\nPRESTAMOS REGISTRADOS:");

        for (String loan : loans) {
            System.out.println(loan);
        }
    }
}