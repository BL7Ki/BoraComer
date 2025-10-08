package pos.java.bora_comer.infra.gateway.reserve.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.reserve.ReserveSearchGateway;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.infra.persistence.repository.reserve.ReserveRepository;


@Component
public class ReserveSearchGatewayImpl implements ReserveSearchGateway {

    private final ReserveRepository reserveRepository;
    private final ReserveMapper reserveMapper;

    public ReserveSearchGatewayImpl(ReserveRepository reserveRepository, ReserveMapper reserveMapper) {
        this.reserveRepository = reserveRepository;
        this.reserveMapper = reserveMapper;
    }

    @Override
    public Reserve findById(Long id) {
        return reserveRepository.findById(id)
                .map(reserveMapper::toDomain)
                .orElseThrow(() -> new SummerNotFoundException("Reserva com ID " + id + " não encontrada."));
    }

    @Override
    public Page<Reserve> findAll(int page, int size) {
        return reserveRepository.findAll(PageRequest.of(page, size))
                .map(reserveMapper::toDomain);
    }
}
