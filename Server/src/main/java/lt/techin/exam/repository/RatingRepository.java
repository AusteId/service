package lt.techin.exam.repository;

import lt.techin.exam.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

  List<Rating> findAllByEmployeeId(Long employeeId);

  Optional<Rating> findByRaterIdAndEmployeeId(Long raterId, Long employeeId);

  Boolean existsByRaterIdAndEmployeeId(Long raterId, Long employeeId);

  List<Rating> findByEmployeeId(Long employeeId);
}
