package exceptions;

public class MatchAlreadyFinishedException extends Exception {

    public MatchAlreadyFinishedException() {
        super("Partida já finalizada!");
    }

    public MatchAlreadyFinishedException(String message) {
        super(message);
    }
}
