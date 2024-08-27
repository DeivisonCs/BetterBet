package exceptions;

public class InvalidAddressException extends Exception{
	public InvalidAddressException() {
		super("Endereço Inválido");
	}
	
	public InvalidAddressException(String message) {
		super(message);
	}
}
