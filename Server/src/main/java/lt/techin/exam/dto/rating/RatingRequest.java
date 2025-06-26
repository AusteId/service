package lt.techin.exam.dto.rating;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record RatingRequest(

        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot be more than 5")
        int rating,

        @Positive(message = "Employee ID must be a positive number")
        long employeeId,

        @Positive(message = "Rater ID must be a positive number")
        long raterId
) {
}
