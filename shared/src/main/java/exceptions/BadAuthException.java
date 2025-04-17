package exceptions;

public class BadAuthException extends RuntimeException {
    public BadAuthException(String message) {
        super(message);
    }
}
