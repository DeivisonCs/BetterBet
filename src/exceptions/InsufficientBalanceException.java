package exceptions;

public class InsufficientBalanceException extends Exception {
	
    public InsufficientBalanceException() {
        super("Saldo Insufuciente!");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
