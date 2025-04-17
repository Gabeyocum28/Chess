package exceptions;

public class AlreadyResignException extends RuntimeException {
    public AlreadyResignException(String message) {
        super(message);
    }
}
