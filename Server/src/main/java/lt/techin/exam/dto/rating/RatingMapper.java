package lt.techin.exam.dto.rating;

import lombok.AllArgsConstructor;
import lt.techin.exam.model.Employee;
import lt.techin.exam.model.Rating;
import lt.techin.exam.model.User;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RatingMapper {

  public Rating toRating(RatingRequest dto, User rater, Employee employee) {
    Rating rating = new Rating();
    rating.setRating(dto.rating());
    rating.setRater(rater);
    rating.setEmployee(employee);
    return rating;
  }

  public RatingResponse toRatingResponse(Employee employee, Rating userRating) {
    return new RatingResponse(
            employee.getAverageRating(),
            employee.getRatingCount(),
            userRating != null ? userRating.getRating() : null,
            employee.getId()
    );
  }
}
