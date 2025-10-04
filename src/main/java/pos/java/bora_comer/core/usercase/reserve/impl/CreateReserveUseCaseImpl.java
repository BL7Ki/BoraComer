package pos.java.bora_comer.core.usercase.reserve.impl;

import org.springframework.stereotype.Service;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.core.gateway.reserve.ReserveCreateGateway;
import pos.java.bora_comer.core.usercase.reserve.CreateReserveUseCase;


@Service
public class CreateReserveUseCaseImpl implements CreateReserveUseCase {

    private final ReserveCreateGateway reserveCreateGateway;

    public CreateReserveUseCaseImpl(ReserveCreateGateway reserveCreateGateway) {
        this.reserveCreateGateway = reserveCreateGateway;
    }

    @Override
    public Reserve execute(Reserve reserve) {
        // Verifica se já existe reserva para o mesmo usuário e data/hora
        boolean exists = reserveCreateGateway.existsByDateTimeReserveAndUserId(
            reserve.getDateTimeReserve(), reserve.getUserId()
        );
        
        if (exists) {
            throw new ReserveDomainException("Já existe uma reserva para este usuário nesta data/hora.");
        }
        return reserveCreateGateway.save(reserve);
    }
}
