package exceptions;

public class InvalidMatchException extends Exception {
	public InvalidMatchException() {
		super("Partida Inválida");
	}
	
	public InvalidMatchException(String message) {
		super(message);
	}
}
