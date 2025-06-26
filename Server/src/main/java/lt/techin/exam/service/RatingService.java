package lt.techin.exam.service;

import lombok.AllArgsConstructor;
import lt.techin.exam.dto.rating.RatingMapper;
import lt.techin.exam.dto.rating.RatingRequest;
import lt.techin.exam.model.Employee;
import lt.techin.exam.model.Rating;
import lt.techin.exam.model.User;
import lt.techin.exam.repository.EmployeeRepository;
import lt.techin.exam.repository.RatingRepository;
import lt.techin.exam.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RatingService {

  private final RatingRepository ratingRepository;
  private final UserRepository userRepository;
  private final EmployeeRepository employeeRepository;
  private final RatingMapper ratingMapper;

  public Rating createRating(RatingRequest dto) {

    User rater = userRepository.findById(dto.raterId())
            .orElseThrow(() -> new RuntimeException("Rater not found"));
    Employee employee = employeeRepository.findById(dto.employeeId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    if (ratingRepository.existsByRaterIdAndEmployeeId(dto.raterId(), dto.employeeId())) {
      throw new RuntimeException("Rating already exists");
    }

    Rating rating = ratingMapper.toRating(dto, rater, employee);
    ratingRepository.save(rating);
    updateEmployeeRating(employee, rating.getRating());
    return rating;
  }

  public Rating updateRating(RatingRequest dto) {

    User rater = userRepository.findById(dto.raterId())
            .orElseThrow(() -> new RuntimeException("Rater not found"));
    Employee employee = employeeRepository.findById(dto.employeeId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    Rating existingRating = ratingRepository.findByRaterIdAndEmployeeId(dto.raterId(), dto.employeeId())
            .orElseThrow(() -> new RuntimeException("Rating not found"));

    int oldRating = existingRating.getRating();
    existingRating.setRating(dto.rating());
    ratingRepository.save(existingRating);
    updateEmployeeRatingForUpdate(employee, oldRating, dto.rating());
    return existingRating;
  }

  public void updateEmployeeRating(Employee employee, int newRating) {
    int ratingCount = employee.getRatingCount();
    Double averageRating = employee.getAverageRating() != null ? employee.getAverageRating() : 0.0;

    if (ratingCount == 0) {
      employee.setRatingCount(1);
      employee.setAverageRating((double) newRating);
    } else {
      double newAverage = (averageRating * ratingCount + newRating) / (ratingCount + 1);
      employee.setAverageRating(newAverage);
      employee.setRatingCount(ratingCount + 1);
    }

    employeeRepository.save(employee);
  }

  private void updateEmployeeRatingForUpdate(Employee employee, int oldRating, int newRating) {

    int ratingCount = employee.getRatingCount();
    Double averageRating = employee.getAverageRating() != null ? employee.getAverageRating() : 0.0;

    double newAverage = (averageRating * ratingCount - oldRating + newRating) / ratingCount;
    employee.setAverageRating(newAverage);
    employeeRepository.save(employee);
  }

  public Optional<Rating> findByRaterIdAndEmployeeId(Long raterId, Long employeeId) {
    return ratingRepository.findByRaterIdAndEmployeeId(raterId, employeeId);
  }
}
