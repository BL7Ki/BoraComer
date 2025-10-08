package pos.java.bora_comer.core.usercase.reserve;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface SearchReserveUseCase {

    Reserve findById(Long id) throws SummerNotFoundException;

    Page<Reserve> findAll(int page, int size) throws ReserveDomainException;
}
