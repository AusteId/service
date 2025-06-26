package lt.techin.exam.repository;

import lt.techin.exam.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

  boolean existsByName(String name);
}
