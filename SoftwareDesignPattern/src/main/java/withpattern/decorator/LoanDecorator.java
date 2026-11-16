
package withpattern.decorator;

public abstract class LoanDecorator implements LoanService {

    protected LoanService service;

    public LoanDecorator(LoanService service) {
        this.service = service;
    }

    @Override
    public void loanBook(String bookTitle, String studentName) {
        service.loanBook(bookTitle, studentName);
    }
}
