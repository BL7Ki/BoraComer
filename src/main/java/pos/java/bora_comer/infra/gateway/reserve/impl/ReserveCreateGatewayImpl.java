package pos.java.bora_comer.infra.gateway.reserve.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.core.gateway.reserve.ReserveCreateGateway;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.infra.persistence.repository.reserve.ReserveRepository;

@Component
public class ReserveCreateGatewayImpl implements ReserveCreateGateway {

    private final ReserveRepository reserveRepository;
    private final ReserveMapper reserveMapper;

    public ReserveCreateGatewayImpl(ReserveRepository reserveRepository, ReserveMapper reserveMapper) {
        this.reserveRepository = reserveRepository;
        this.reserveMapper = reserveMapper;
    }

    @Override
    public boolean existsByDateTimeReserveAndUserId(LocalDateTime dateTimeReserve, Long userId) {
        return reserveRepository.existsByDateTimeReserveAndUserId(dateTimeReserve, userId);
    }

    @Transactional
    @Override
    public Reserve save(Reserve reserve) {
        
        if (reserve == null) {
            throw new ReserveDomainException("Reserva não pode ser nulo");
        }

       if (existsByDateTimeReserveAndUserId(reserve.getDateTimeReserve(), reserve.getUserId())) {
            throw new ReserveDomainException("Já existe uma reserva com esse usuario, data e hora.");
        }   

        var reserveEntity = reserveMapper.toEntity(reserve);
        var savedEntity = reserveRepository.save(reserveEntity);

        return reserveMapper.toDomain(savedEntity);
    }
}
