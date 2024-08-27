package exceptions;

public class InvalidBirthDateException extends Exception{
	public InvalidBirthDateException() {
		super("Data de nascimento inválida");
	}
	
	public InvalidBirthDateException(String message) {
		super(message);
	}
}
