package pos.java.bora_comer.infra.persistence.repository.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {

    boolean existsByName(String name);

    // Achar pelo OwnerId
    @Query("SELECT r FROM RestaurantEntity r WHERE r.ownerId = :ownerId")
    List<RestaurantEntity> findByOwnerId(@Param("ownerId") Long ownerId);
}
