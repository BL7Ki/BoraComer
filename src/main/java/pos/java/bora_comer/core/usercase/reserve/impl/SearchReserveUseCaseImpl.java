package pos.java.bora_comer.core.usercase.reserve.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.gateway.reserve.ReserveSearchGateway;
import pos.java.bora_comer.core.usercase.reserve.SearchReserveUseCase;


@Service
public class SearchReserveUseCaseImpl implements SearchReserveUseCase {

    private final ReserveSearchGateway reserveSearchGateway;

    public SearchReserveUseCaseImpl(ReserveSearchGateway reserveSearchGateway) {
        this.reserveSearchGateway = reserveSearchGateway;
    }

    @Override
    public Reserve findById(Long id) {
        return reserveSearchGateway.findById(id);
    }

    @Override
    public Page<Reserve> findAll(int page, int size) {
        return reserveSearchGateway.findAll(page, size);
    }
}
