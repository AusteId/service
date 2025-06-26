package lt.techin.exam.dto.employee;

import lt.techin.exam.dto.shop.ShopResponse;

import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String firstname,
        String lastname,
        LocalDate birthDate,
        String address,
        String phone,
        String email,
        String city,
        String specialization,
        String description,
        ShopResponse shop
) {
}
