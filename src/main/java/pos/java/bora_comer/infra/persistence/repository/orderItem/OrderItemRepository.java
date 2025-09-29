package pos.java.bora_comer.infra.persistence.repository.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
    List<OrderItemEntity> findByMenuItemId(Long menuItemId);
    List<OrderItemEntity> findByOrderId(Long orderId);
    List<OrderItemEntity> findByOrderIdAndMenuItemId(Long orderId, Long menuItemId);

}
