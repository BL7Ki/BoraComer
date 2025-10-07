package pos.java.bora_comer.infra.gateway.reserve.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.core.gateway.reserve.ReserveUpdateGateway;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.infra.persistence.repository.reserve.ReserveRepository;
import pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import java.util.Optional;

@Component
public class ReserveUpdateGatewayImpl implements ReserveUpdateGateway {

    private final ReserveRepository reserveRepository;
    private final ReserveMapper reserveMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public ReserveUpdateGatewayImpl(ReserveRepository reserveRepository,
                                    ReserveMapper reserveMapper,
                                    RestaurantRepository restaurantRepository,
                                    UserRepository userRepository) {
        this.reserveRepository = reserveRepository;
        this.reserveMapper = reserveMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Reserve update(Reserve reserve) throws ReserveDomainException {
        ReserveEntity entity = reserveRepository.findById(reserve.getId())
                .orElseThrow(() -> new ReserveDomainException("Reserva com ID " + reserve.getId() + " não encontrado."));

        if (!reserve.getRestaurantId().equals(entity.getRestaurantId())) {
            restaurantRepository.findById(reserve.getRestaurantId())
                    .orElseThrow(() -> new ReserveDomainException("Restaurante com ID " + reserve.getRestaurantId() + " não encontrado."));
            entity.updateRestaurantId(reserve.getRestaurantId());
        }

        if (!reserve.getUserId().equals(entity.getUserId())) {
            userRepository.findById(reserve.getUserId())
                    .orElseThrow(() -> new ReserveDomainException("Usuário com ID " + reserve.getUserId() + " não encontrado."));
            entity.updateUserId(reserve.getUserId());
        }

        entity.updateDateTimeReserve(reserve.getDateTimeReserve());
        entity.updateQuantity(reserve.getQuantity());
        entity.updateLastModifiedDate();

       ReserveEntity updatedEntity = reserveRepository.save(entity);
        return reserveMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Reserve> findById(Long id) {
        return reserveRepository.findById(id)
                .map(reserveMapper::toDomain);
    }
}
