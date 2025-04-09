package entity;

public class CarLoan extends Loan {
    private String carModel;
    private int carValue;

    public CarLoan() {}

    public CarLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm, String loanStatus, String carModel, int carValue) {
        this.setLoanId(loanId);
        this.setCustomer(customer);
        this.setPrincipalAmount(principalAmount);
        this.setInterestRate(interestRate);
        this.setLoanTerm(loanTerm);
        this.setLoanType("CarLoan");
        this.setLoanStatus(loanStatus);
        this.carModel = carModel;
        this.carValue = carValue;
    }

    // Getters and Setters
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarValue() {
        return carValue;
    }

    public void setCarValue(int carValue) {
        this.carValue = carValue;
    }

    // toString
    @Override
    public String toString() {
        return "CarLoan [carModel=" + carModel + ", carValue=" + carValue + "]";
    }   
}
