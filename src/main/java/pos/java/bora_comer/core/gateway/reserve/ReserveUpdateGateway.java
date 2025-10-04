package pos.java.bora_comer.core.gateway.reserve;

import java.util.Optional;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;

public interface ReserveUpdateGateway {

    Reserve update(Reserve reserve) throws ReserveDomainException;

    Optional<Reserve> findById(Long id);
}
