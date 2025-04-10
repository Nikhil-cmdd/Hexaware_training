package dao;

import entity.Loan;
import java.util.List;
import exception.InvalidLoanException;

public interface ILoanRepository {
    boolean applyLoan(Loan loan);
    double calculateInterest(int loanId) throws InvalidLoanException;
    double calculateInterest(double principal, double rate, int tenure); // overloaded

    boolean loanStatus(int loanId) throws InvalidLoanException;

    double calculateEMI(int loanId) throws InvalidLoanException;
    double calculateEMI(double principal, double rate, int tenure); // overloaded

    boolean loanRepayment(int loanId, double amount) throws InvalidLoanException;

    List<Loan> getAllLoan();
    Loan getLoanById(int loanId) throws InvalidLoanException;
}
