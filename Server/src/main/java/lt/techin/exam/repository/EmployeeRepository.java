package lt.techin.exam.repository;

import lt.techin.exam.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  boolean existsByEmail(String email);

  List<Employee> findByShopId(Long shopId);
}
