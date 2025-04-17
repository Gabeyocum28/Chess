package exceptions;

public class BadGameException extends RuntimeException {
    public BadGameException(String message) {
        super(message);
    }
}
