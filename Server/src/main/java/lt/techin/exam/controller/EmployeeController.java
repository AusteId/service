package lt.techin.exam.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lt.techin.exam.dto.employee.CreateEmployeeRequest;
import lt.techin.exam.dto.employee.EmployeeMapper;
import lt.techin.exam.dto.employee.EmployeeResponse;
import lt.techin.exam.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {

  private final EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<List<EmployeeResponse>> getEmployees() {
    return ResponseEntity.ok(employeeService.getAllEmployees());
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id) {
    return ResponseEntity.ok(employeeService.getEmployeeById(id));
  }

  @PostMapping("/{shopId}")
  public ResponseEntity<EmployeeResponse> createEmployee(@PathVariable Long shopId, @Valid @RequestBody CreateEmployeeRequest dto) {
    EmployeeResponse savedEmployee = employeeService.createEmployee(shopId, dto);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedEmployee.id())
                            .toUri())
            .body(savedEmployee);
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable Long id, @Valid @RequestBody CreateEmployeeRequest dto) {
    EmployeeResponse updateEmployee = employeeService.updateEmployee(id, dto);
    return ResponseEntity.ok(updateEmployee);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    employeeService.deleteEmployee(id);
    return ResponseEntity.noContent().build();
  }
}
