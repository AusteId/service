package lt.techin.exam.validation;

import jakarta.persistence.EntityNotFoundException;
import lt.techin.exam.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException e) {
    Map<String, String> errors = new HashMap<>();

    e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(),
            error.getDefaultMessage())
    );

    e.getBindingResult().getGlobalErrors().forEach(error ->
            errors.put(error.getObjectName() + "_global", error.getDefaultMessage())
    );

    return ResponseEntity.badRequest().body(errors);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleEmailAlreadyExists(EmailAlreadyExistsException e) {
    return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleEntityNotFound(EntityNotFoundException e) {
    return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ShopNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleShopNotFound(ShopNotFoundException e) {
    return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ShopAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleShopAlreadyExists(ShopAlreadyExistsException e) {
    return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmployeeAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleEmployeeAlreadyExists(EmployeeAlreadyExistsException e) {
    return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(EmployeeNotFoundException.class)
  public ResponseEntity<Map<String, String>> handleEmployeeNotFound(EmployeeNotFoundException e) {
    return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
  }
}
