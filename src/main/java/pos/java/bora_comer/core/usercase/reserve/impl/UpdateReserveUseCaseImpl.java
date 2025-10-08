package pos.java.bora_comer.core.usercase.reserve.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.core.gateway.reserve.ReserveUpdateGateway;
import pos.java.bora_comer.core.usercase.reserve.UpdateReserveUseCase;

@Service
public class UpdateReserveUseCaseImpl implements UpdateReserveUseCase {

    private final ReserveUpdateGateway reserveUpdateGateway;

    public UpdateReserveUseCaseImpl(ReserveUpdateGateway reserveUpdateGateway) {
        this.reserveUpdateGateway = reserveUpdateGateway;
    }

    @Override
    public Reserve execute(Reserve reserve) {
        return reserveUpdateGateway.update(reserve);
    }

     @Override
    public Reserve findById(Long id) throws ReserveDomainException {
        return reserveUpdateGateway.findById(id)
                .orElseThrow(() -> new ReserveDomainException("Reserve com ID " + id + " não encontrado"));
    }
}
