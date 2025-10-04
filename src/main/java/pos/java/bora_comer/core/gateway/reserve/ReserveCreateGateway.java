package pos.java.bora_comer.core.gateway.reserve;

import java.time.LocalDateTime;

import pos.java.bora_comer.core.domain.reserve.Reserve;

public interface ReserveCreateGateway {
    boolean existsByDateTimeReserveAndUserId(LocalDateTime dateTime, Long userId);
    Reserve save(Reserve reserve);
}
