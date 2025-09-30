package pos.java.bora_comer.infra.persistence.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByDeliveryTrue();
    List<OrderEntity> findByDeliveryFalse();
    List<OrderEntity> findByUserId(Long userId);
    List<OrderEntity> findByRestaurantId(Long restaurantId);
    List<OrderEntity> findByRestaurantIdAndUserId(Long restaurantId, Long userId);

}
