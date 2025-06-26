package lt.techin.exam.dto.shop;

import lt.techin.exam.dto.employee.EmployeeResponse;

import java.util.List;

public record ShopResponse(
        Long id,
        String name,
        String address,
        String phone,
        String email,
        String city,
        String description,
        String workingHours,
        String imagePath,
        List<EmployeeResponse> employees
) {
}
