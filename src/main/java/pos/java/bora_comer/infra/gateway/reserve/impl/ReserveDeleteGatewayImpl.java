package pos.java.bora_comer.infra.gateway.reserve.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.reserve.ReserveDeleteGateway;
import pos.java.bora_comer.infra.persistence.repository.reserve.ReserveRepository;

@Component
public class ReserveDeleteGatewayImpl implements ReserveDeleteGateway {

    private final ReserveRepository reserveRepository;

    public ReserveDeleteGatewayImpl(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        var reserveEntity = reserveRepository.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("Reserva com ID " + id + " não encontrada."));

        reserveRepository.delete(reserveEntity);
    }
}
