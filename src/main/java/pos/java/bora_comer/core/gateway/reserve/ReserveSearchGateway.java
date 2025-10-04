package pos.java.bora_comer.core.gateway.reserve;

import org.springframework.data.domain.Page;

import pos.java.bora_comer.core.domain.reserve.Reserve;


public interface ReserveSearchGateway {

    Reserve findById(Long id);

    Page<Reserve> findAll(int page, int size);
}
