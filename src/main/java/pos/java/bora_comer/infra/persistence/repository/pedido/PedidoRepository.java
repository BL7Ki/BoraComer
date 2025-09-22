package pos.java.bora_comer.infra.persistence.repository.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    List<PedidoEntity> findByDeliveryTrue();
    List<PedidoEntity> findByDeliveryFalse();
    List<PedidoEntity> findByUserId(Long userId);
    List<PedidoEntity> findByRestaurantId(Long restaurantId);
    List<PedidoEntity> findByRestaurantIdAndUserId(Long restaurantId, Long userId);

}
