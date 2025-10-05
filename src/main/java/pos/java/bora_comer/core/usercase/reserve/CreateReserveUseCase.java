package pos.java.bora_comer.core.usercase.reserve;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;

public interface CreateReserveUseCase {

    Reserve execute(Reserve order) throws ReserveDomainException;
}
