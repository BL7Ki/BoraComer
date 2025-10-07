package pos.java.bora_comer.infra.persistence.repository.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> findByDeliveryTrue();
    List<MenuItemEntity> findByNameContainingIgnoreCase(String name);

    boolean existsByNameAndRestaurantId(String name, Long restaurantId);
}
