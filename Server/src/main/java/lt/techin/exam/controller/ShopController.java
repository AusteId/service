package lt.techin.exam.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lt.techin.exam.dto.employee.EmployeeResponse;
import lt.techin.exam.dto.shop.CreateShopRequest;
import lt.techin.exam.dto.shop.ShopMapper;
import lt.techin.exam.dto.shop.ShopResponse;
import lt.techin.exam.model.Shop;
import lt.techin.exam.service.EmployeeService;
import lt.techin.exam.service.ShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
@AllArgsConstructor
public class ShopController {

  private final ShopService shopService;
  private final ShopMapper shopMapper;
  private final EmployeeService employeeService;

  @GetMapping
  public ResponseEntity<List<ShopResponse>> getShops() {
    return ResponseEntity.ok(shopService.getAllShops());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ShopResponse> getShop(@PathVariable Long id) {
    return ResponseEntity.ok(shopService.getShopById(id));
  }

  @GetMapping("/{shopId}/employees")
  public ResponseEntity<List<EmployeeResponse>> getEmployeesByShop(@PathVariable Long shopId) {
    return ResponseEntity.ok(employeeService.getEmployeesByShopId(shopId));
  }

  @PostMapping
  public ResponseEntity<ShopResponse> createShop(@Valid @RequestBody CreateShopRequest dto) {
    ShopResponse savedShop = shopService.createShop(dto);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedShop.id())
                            .toUri())
            .body(savedShop);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ShopResponse> updateShop(@PathVariable Long id, @Valid @RequestBody CreateShopRequest dto) {
    ShopResponse updatedShop = shopService.updateShop(id, dto);
    return ResponseEntity.ok(updatedShop);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
    shopService.deleteShop(id);
    return ResponseEntity.noContent().build();
  }
}
