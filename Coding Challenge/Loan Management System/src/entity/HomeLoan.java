package entity;

public class HomeLoan extends Loan {
    private String propertyAddress;
    private int propertyValue;

    public HomeLoan() {}

    public HomeLoan(int loanId, Customer customer, double principalAmount, double interestRate, int loanTerm, String loanStatus, String propertyAddress, int propertyValue) {
    	this.setLoanId(loanId);
    	this.setCustomer(customer);
    	this.setPrincipalAmount(principalAmount);
    	this.setInterestRate(interestRate);
    	this.setLoanTerm(loanTerm);
    	this.setLoanType("HomeLoan");
    	this.setLoanStatus(loanStatus);
    	this.propertyAddress = propertyAddress;
    	this.propertyValue = propertyValue;
}
 
    // Getters and Setters
	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	public int getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(int propertyValue) {
		this.propertyValue = propertyValue;
	}
	
	// toString
	@Override
	public String toString() {
		return "HomeLoan [propertyAddress=" + propertyAddress + ", propertyValue=" + propertyValue + "]";
	}
}
