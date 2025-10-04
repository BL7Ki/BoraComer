package pos.java.bora_comer.infra.persistence.repository.reserve;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<ReserveEntity, Long> {
    List<ReserveEntity> findByUserId(Long userId);
    List<ReserveEntity> findByRestaurantId(Long restaurantId);
    List<ReserveEntity> findByRestaurantIdAndUserId(Long restaurantId, Long userId);
    
    boolean existsByDateTimeReserveAndUserId(LocalDateTime dateTimeReserve, Long userId);
}
