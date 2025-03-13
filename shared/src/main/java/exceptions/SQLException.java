package exceptions;

public class SQLException extends RuntimeException {
  public SQLException(String message) {
    super(message);
  }
}
