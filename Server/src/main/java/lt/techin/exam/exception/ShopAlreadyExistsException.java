package lt.techin.exam.exception;

public class ShopAlreadyExistsException extends RuntimeException {
  public ShopAlreadyExistsException(String message) {
    super(message);
  }
}
