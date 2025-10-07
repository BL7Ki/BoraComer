package pos.java.bora_comer.core.mapper.reserve.impl;

import org.springframework.stereotype.Component;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.core.mapper.reserve.ReserveMapper;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveRequestDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveResponseDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity;

@Component
public class ReserveMapperImpl implements ReserveMapper {

    @Override
    public Reserve toDomain(ReserveRequestDTO reserveRequestDTO) {
        if (reserveRequestDTO == null) {
            throw new ReserveDomainException("ReserveRequestDTO não pode ser nulo");
        }

        return Reserve.create(
                reserveRequestDTO.dateTimeReserve(),
                reserveRequestDTO.quantity(),
                reserveRequestDTO.restaurantId(),
                reserveRequestDTO.userId(),
                reserveRequestDTO.lastModifiedDate()
        );
    }

    @Override
    public ReserveEntity toEntity(Reserve reserve) {
        if (reserve == null) {
            throw new ReserveDomainException("Reserva não pode ser nulo");
        }

        return ReserveEntity.create(
                reserve.getDateTimeReserve(),
                reserve.getQuantity(),
                reserve.getRestaurantId(),
                reserve.getUserId() 
        );
    }

    @Override
    public Reserve toDomain(ReserveEntity reserveEntity) {
        if (reserveEntity == null) {
            throw new ReserveDomainException("ReserveEntity não pode ser nulo");
        }

        return Reserve.create(
                reserveEntity.getId(),
                reserveEntity.getDateTimeReserve(),
                reserveEntity.getQuantity(),
                reserveEntity.getRestaurantId(),
                reserveEntity.getUserId(),
                reserveEntity.getLastModifiedDate()
        );
    }

    @Override
    public ReserveResponseDTO toResponseDTO(Reserve reserve) {
        if (reserve == null) {
            throw new ReserveDomainException("Reserva não pode ser nulo");
        }

        return new ReserveResponseDTO(
                reserve.getId(),
                reserve.getDateTimeReserve(),
                reserve.getQuantity(),
                reserve.getRestaurantId(),
                reserve.getUserId(),
                reserve.getLastModifiedDate()
        );
    }

    @Override
    public Reserve toDomain(ReserveUpdateRequestDTO reserveUpdateRequestDTO, Long id, Long restaurantId, Long userId) {
        if (reserveUpdateRequestDTO == null) {
            throw new ReserveDomainException("ReserveUpdateRequestDTO não pode ser nulo");
        }

        return Reserve.create(
                id,
                reserveUpdateRequestDTO.dateTimeReserve(),
                reserveUpdateRequestDTO.quantity(),  
                restaurantId, // preserva o restaurantId que vem do parâmetro
                userId, // preserva o userId que vem do parâmetro
                reserveUpdateRequestDTO.lastModifiedDate()
        );
    }
}
