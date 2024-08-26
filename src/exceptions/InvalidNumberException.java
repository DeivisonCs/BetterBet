package exceptions;

public class InvalidNumberException extends Exception {
    public InvalidNumberException() {
        super("Valor inválido");
    }

    public InvalidNumberException(String message) {
        super(message);
    }
}
