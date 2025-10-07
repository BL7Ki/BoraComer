package pos.java.bora_comer.core.gateway.reserve;

import java.util.List;
import java.util.Optional;

import pos.java.bora_comer.core.domain.reserve.Reserve;

public interface ReserveGateway {
    Reserve save(Reserve reserve);
    List<Reserve> findAll();
    Optional<Reserve> findById(Long id);
    Reserve update(Reserve reserve);
    void deleteById(Long id);
}
