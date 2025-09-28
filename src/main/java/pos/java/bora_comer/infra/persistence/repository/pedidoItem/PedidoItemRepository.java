package pos.java.bora_comer.infra.persistence.repository.pedidoItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity;
import java.util.List;

@Repository
public interface PedidoItemRepository extends JpaRepository<PedidoItemEntity, Long> {
    List<PedidoItemEntity> findByDeliveryTrue();
    List<PedidoItemEntity> findByDeliveryFalse();
    List<PedidoItemEntity> findByMenuItemId(Long menuItemId);
    List<PedidoItemEntity> findByPedidoId(Long pedidoId);
    List<PedidoItemEntity> findByPedidoIdAndMenuItemId(Long pedidoId, Long menuItemId);

}
