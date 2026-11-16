
package withpattern.decorator;

public class BasicLoanService implements LoanService {

    @Override
    public void loanBook(String bookTitle, String studentName) {

        System.out.println("Préstamo realizado:");
        System.out.println(studentName + " ha tomado prestado " + bookTitle);
    }
}
