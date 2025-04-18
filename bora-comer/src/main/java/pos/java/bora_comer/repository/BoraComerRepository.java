package pos.java.bora_comer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoraComerRepository extends JpaRepository<Long, String> {

}
