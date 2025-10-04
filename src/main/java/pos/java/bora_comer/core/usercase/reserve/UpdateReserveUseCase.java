package pos.java.bora_comer.core.usercase.reserve;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;

public interface UpdateReserveUseCase {
    Reserve execute(Reserve reserve) throws ReserveDomainException;

    Reserve findById(Long id) throws ReserveDomainException;
}
