package exceptions;

public class InvalidNameException extends Exception{
	public InvalidNameException() {
		super("Email Inválido");
	}
	
	public InvalidNameException(String message) {
		super(message);
	}
}
