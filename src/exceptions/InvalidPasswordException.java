package exceptions;

public class InvalidPasswordException extends Exception{
	public InvalidPasswordException() {
		super("Senha Inválida");
	}
	
	public InvalidPasswordException(String message) {
		super(message);
	}
}
