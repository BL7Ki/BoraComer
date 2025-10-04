package pos.java.bora_comer.core.usercase.reserve.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.gateway.reserve.ReserveDeleteGateway;
import pos.java.bora_comer.core.usercase.reserve.DeleteReserveUseCase;

@Service
public class DeleteReserveUseCaseImpl implements DeleteReserveUseCase {

    private final ReserveDeleteGateway reserveDeleteGateway;

    public DeleteReserveUseCaseImpl(ReserveDeleteGateway reserveDeleteGateway) {
        this.reserveDeleteGateway = reserveDeleteGateway;
    }

    @Override
    public void execute(Long id) {
        reserveDeleteGateway.deleteById(id);
    }
}
