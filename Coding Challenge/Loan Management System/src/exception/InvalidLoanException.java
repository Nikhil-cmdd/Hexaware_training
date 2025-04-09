package exception;

public class InvalidLoanException extends Exception {
	
	private static final long serialVersionUID = 1L;

    public InvalidLoanException() {
        super("Invalid loan ID or loan not found.");
    }

    public InvalidLoanException(String message) {
        super(message);
    }
}
