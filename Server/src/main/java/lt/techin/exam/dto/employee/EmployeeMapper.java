package lt.techin.exam.dto.employee;

import lombok.AllArgsConstructor;
import lt.techin.exam.dto.shop.ShopMapper;
import lt.techin.exam.dto.shop.ShopResponse;
import lt.techin.exam.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

  public EmployeeResponse toEmployeeResponse(Employee employee) {
    return new EmployeeResponse(
            employee.getId(),
            employee.getFirstname(),
            employee.getLastname(),
            employee.getBirthDate(),
            employee.getAddress(),
            employee.getPhone(),
            employee.getEmail(),
            employee.getCity(),
            employee.getSpecialization(),
            employee.getDescription(),
            employee.getShop() != null ? new ShopResponse(
                    employee.getShop().getId(),
                    employee.getShop().getName(),
                    employee.getShop().getAddress(),
                    employee.getShop().getPhone(),
                    employee.getShop().getEmail(),
                    employee.getShop().getCity(),
                    employee.getShop().getDescription(),
                    employee.getShop().getWorkingHours(),
                    employee.getShop().getImagePath(),
                    null
            ) : null
    );
  }

  public Employee toEmployee(CreateEmployeeRequest dto) {
    Employee employee = new Employee();
    employee.setFirstname(dto.firstname());
    employee.setLastname(dto.lastname());
    employee.setBirthDate(dto.birthDate());
    employee.setAddress(dto.address());
    employee.setPhone(dto.phone());
    employee.setEmail(dto.email());
    employee.setCity(dto.city());
    employee.setSpecialization(dto.specialization());
    employee.setDescription(dto.description());

    return employee;
  }
}
