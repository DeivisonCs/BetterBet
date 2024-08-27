package exceptions;

public class InvalidCpfException extends Exception{
	public InvalidCpfException() {
		super("CPF Inválido");
	}
	
	public InvalidCpfException(String message) {
		super(message);
	}
}
