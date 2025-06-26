package lt.techin.exam.service;

import lombok.AllArgsConstructor;
import lt.techin.exam.dto.employee.CreateEmployeeRequest;
import lt.techin.exam.dto.employee.EmployeeMapper;
import lt.techin.exam.dto.employee.EmployeeResponse;
import lt.techin.exam.exception.EmployeeAlreadyExistsException;
import lt.techin.exam.exception.EmployeeNotFoundException;
import lt.techin.exam.exception.ShopNotFoundException;
import lt.techin.exam.model.Employee;
import lt.techin.exam.model.Shop;
import lt.techin.exam.repository.EmployeeRepository;
import lt.techin.exam.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

  private final EmployeeRepository employeeRepository;
  private final EmployeeMapper employeeMapper;
  private final ShopRepository shopRepository;

  public EmployeeResponse createEmployee(Long shopId, CreateEmployeeRequest dto) {

//    if (employeeRepository.existsByEmail(dto.email())) {
//      throw new EmployeeAlreadyExistsException("Employee with this firstname and lastname already exists");
//    }

    Shop shop = shopRepository.findById(shopId)
            .orElseThrow(() -> new RuntimeException("Shop not found"));

    Employee employee = employeeMapper.toEmployee(dto);
//    Shop shop = shopRepository.findById(shopId)
//            .orElseThrow(() -> new ShopNotFoundException("Shop not found"));
    employee.setShop(shop);
    employee.setRatingCount(0);
    employee.setAverageRating(null);
    Employee savedEmployee = employeeRepository.save(employee);

    return employeeMapper.toEmployeeResponse(savedEmployee);
  }

  public EmployeeResponse updateEmployee(Long id, CreateEmployeeRequest dto) {

    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("This employee is not found"));

    if (!employee.getEmail().equals(dto.email()) && employeeRepository.existsByEmail(dto.email())) {
      throw new EmployeeAlreadyExistsException("Employee with this email already exists");
    }

    employee.setFirstname(dto.firstname());
    employee.setLastname(dto.lastname());
    employee.setBirthDate(dto.birthDate());
    employee.setAddress(dto.address());
    employee.setPhone(dto.phone());
    employee.setEmail(dto.email());
    employee.setCity(dto.city());
    employee.setSpecialization(dto.specialization());
    employee.setDescription(dto.description());
    Employee savedEmployee = employeeRepository.save(employee);

    return employeeMapper.toEmployeeResponse(savedEmployee);
  }

  public void deleteEmployee(Long id) {

    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("This employee is not found"));

    employeeRepository.delete(employee);
  }

  public EmployeeResponse getEmployeeById(Long id) {
    Employee employee = employeeRepository.findById(id)
            .orElseThrow(() -> new EmployeeNotFoundException("Employee with ID " + id + " not found"));

    return employeeMapper.toEmployeeResponse(employee);
  }

  public List<EmployeeResponse> getAllEmployees() {
    return employeeRepository.findAll().stream()
            .map(employeeMapper::toEmployeeResponse)
            .toList();
  }

  public List<EmployeeResponse> getEmployeesByShopId(Long shopId) {
    List<Employee> employees = employeeRepository.findByShopId(shopId);

    return employees.stream()
            .map(employeeMapper::toEmployeeResponse)
            .toList();
  }

  public Optional<Employee> findById(Long id) {
    return employeeRepository.findById(id);
  }
}
