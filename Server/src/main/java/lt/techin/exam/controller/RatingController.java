package lt.techin.exam.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lt.techin.exam.dto.rating.RatingMapper;
import lt.techin.exam.dto.rating.RatingRequest;
import lt.techin.exam.dto.rating.RatingResponse;
import lt.techin.exam.model.Employee;
import lt.techin.exam.model.Rating;
import lt.techin.exam.model.User;
import lt.techin.exam.service.EmployeeService;
import lt.techin.exam.service.RatingService;
import lt.techin.exam.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api/employees/{employeeId}/ratings")
@AllArgsConstructor
public class RatingController {

  private final RatingService ratingService;
  private final UserService userService;
  private final RatingMapper ratingMapper;
  private final EmployeeService employeeService;

  @GetMapping("/summary")
  public ResponseEntity<RatingResponse> getEmployeeRatingSummary(@PathVariable("employeeId") Long employeeId) {
    Employee employee = employeeService.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));
    RatingResponse response = ratingMapper.toRatingResponse(employee, null);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/rater")
  public ResponseEntity<RatingResponse> getUserRating(@PathVariable Long employeeId) {

    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User rater = userService.findByEmail(currentUserEmail)
            .orElseThrow(() -> new RuntimeException("Current user not found"));

    Employee employee = employeeService.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    Optional<Rating> userRating = ratingService.findByRaterIdAndEmployeeId(rater.getId(), employeeId);
    RatingResponse response = ratingMapper.toRatingResponse(employee, userRating.orElse(null));

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<Void> createRating(@PathVariable Long employeeId, @Valid @RequestBody RatingRequest dto) {

    if (!employeeId.equals(dto.employeeId())) {
      throw new IllegalArgumentException("Employee ID in path and body must match");
    }

    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User rater = userService.findByEmail(currentUserEmail)
            .orElseThrow(() -> new RuntimeException("Current user not found"));

    RatingRequest adjustedDto = new RatingRequest(dto.rating(), dto.employeeId(), rater.getId());
    Rating rating = ratingService.createRating(adjustedDto);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(rating.getId())
                            .toUri())
            .build();
  }

  @PutMapping
  public ResponseEntity<Void> updateRating(@PathVariable Long employeeId, @Valid @RequestBody RatingRequest dto) {

    if (!employeeId.equals(dto.employeeId())) {
      throw new IllegalArgumentException("Employee ID in path and body must match");
    }

    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    User rater = userService.findByEmail(currentUserEmail)
            .orElseThrow(() -> new RuntimeException("Current user not found"));

    RatingRequest adjustedDto = new RatingRequest(dto.rating(), dto.employeeId(), rater.getId());
    ratingService.updateRating(adjustedDto);

    return ResponseEntity.noContent().build();
  }


}
