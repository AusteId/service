package lt.techin.exam.service;

import lombok.AllArgsConstructor;
import lt.techin.exam.dto.shop.CreateShopRequest;
import lt.techin.exam.dto.shop.ShopMapper;
import lt.techin.exam.dto.shop.ShopResponse;
import lt.techin.exam.exception.ShopAlreadyExistsException;
import lt.techin.exam.exception.ShopNotFoundException;
import lt.techin.exam.model.Shop;
import lt.techin.exam.repository.ShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShopService {

  private final ShopRepository shopRepository;
  private final ShopMapper shopMapper;

  public ShopResponse createShop(CreateShopRequest dto) {

    if (shopRepository.existsByName(dto.name())) {
      throw new ShopAlreadyExistsException("Shop already exists");
    }

    Shop shop = shopMapper.toShop(dto);
    Shop savedShop = shopRepository.save(shop);

    return shopMapper.toShopResponse(savedShop);
  }

  public ShopResponse updateShop(Long shopId, CreateShopRequest dto) {

    Shop shop = shopRepository.findById(shopId)
            .orElseThrow(() -> new ShopNotFoundException("This shop is not found"));

    if (!shop.getName().equals(dto.name()) && shopRepository.existsByName(dto.name())) {
      throw new ShopAlreadyExistsException("Shop with this name already exists");
    }

    shop.setName(dto.name());
    shop.setAddress(dto.address());
    shop.setPhone(dto.phone());
    shop.setEmail(dto.email());
    shop.setCity(dto.city());
    shop.setDescription(dto.description());
    shop.setWorkingHours(dto.workingHours());
    shop.setImagePath(dto.imagePath());
    Shop savedShop = shopRepository.save(shop);

    return shopMapper.toShopResponse(savedShop);
  }

  public void deleteShop(Long shopId) {

    Shop shop = shopRepository.findById(shopId)
            .orElseThrow(() -> new ShopNotFoundException("This shop is not found"));

    shopRepository.delete(shop);
  }

  public ShopResponse getShopById(Long shopId) {
    Shop shop = shopRepository.findById(shopId)
            .orElseThrow(() -> new ShopNotFoundException("Shop with ID " + shopId + " not found"));

    return shopMapper.toShopResponse(shop);
  }

  public List<ShopResponse> getAllShops() {
    return shopRepository.findAll().stream()
            .map(shopMapper::toShopResponse)
            .toList();
  }
}
