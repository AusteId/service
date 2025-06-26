package lt.techin.exam.dto.rating;

public record RatingResponse(
        Double averageRating,
        int ratingCount,
        Integer userRating,
        long employeeId
) {
}
