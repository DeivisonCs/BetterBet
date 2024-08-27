package exceptions;

public class InvalidEmailException extends Exception{
	public InvalidEmailException() {
		super("Email Inválido");
	}
	
	public InvalidEmailException(String message) {
		super(message);
	}
}
