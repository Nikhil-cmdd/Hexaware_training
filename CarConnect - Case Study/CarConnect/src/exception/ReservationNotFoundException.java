package exception;

public class ReservationNotFoundException extends RuntimeException {
	public static final long serialVersionUID =  1L;
	
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
