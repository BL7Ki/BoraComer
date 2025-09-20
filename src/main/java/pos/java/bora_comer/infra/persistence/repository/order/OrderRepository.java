package pos.java.bora_comer.infra.persistence.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pos.java.bora_comer.core.domain.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
