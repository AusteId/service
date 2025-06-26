package lt.techin.exam.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {
  public EmployeeAlreadyExistsException(String message) {
    super(message);
  }
}
