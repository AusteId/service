package lt.techin.exam.dto.shop;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lt.techin.exam.dto.employee.EmployeeMapper;
import lt.techin.exam.model.Employee;
import lt.techin.exam.model.Shop;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ShopMapper {

  private final EmployeeMapper employeeMapper;

  public ShopResponse toShopResponse(Shop shop) {
    return new ShopResponse(
            shop.getId(),
            shop.getName(),
            shop.getAddress(),
            shop.getPhone(),
            shop.getEmail(),
            shop.getCity(),
            shop.getDescription(),
            shop.getWorkingHours(),
            shop.getImagePath(),
//            Collections.emptyList()
            shop.getEmployees() != null ? shop.getEmployees().stream()
                    .map(employeeMapper::toEmployeeResponse)
                    .collect(Collectors.toList())
                    : Collections.emptyList()
    );
  }

  public Shop toShop(CreateShopRequest dto) {
    Shop shop = new Shop();
    shop.setName(dto.name());
    shop.setAddress(dto.address());
    shop.setPhone(dto.phone());
    shop.setEmail(dto.email());
    shop.setCity(dto.city());
    shop.setDescription(dto.description());
    shop.setWorkingHours(dto.workingHours());
    shop.setImagePath(dto.imagePath());
    shop.setEmployees(Collections.emptyList());

    return shop;
  }
}
