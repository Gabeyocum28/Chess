package exceptions;

public class BadMoveException extends RuntimeException {
    public BadMoveException(String message) {
        super(message);
    }
}
