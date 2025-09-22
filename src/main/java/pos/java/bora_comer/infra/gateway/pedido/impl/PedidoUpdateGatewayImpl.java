package pos.java.bora_comer.infra.gateway.pedido.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.PedidoDomainException;
import pos.java.bora_comer.core.gateway.pedido.PedidoUpdateGateway;
import pos.java.bora_comer.core.mapper.pedido.PedidoMapper;
import pos.java.bora_comer.infra.persistence.repository.pedido.PedidoRepository;
import pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import java.util.Optional;

@Component
public class PedidoUpdateGatewayImpl implements PedidoUpdateGateway {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public PedidoUpdateGatewayImpl(PedidoRepository pedidoRepository,
                                   PedidoMapper pedidoMapper,
                                   RestaurantRepository restaurantRepository,
                                   UserRepository userRepository) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Pedido update(Pedido pedido) throws PedidoDomainException {
        PedidoEntity entity = pedidoRepository.findById(pedido.getId())
                .orElseThrow(() -> new PedidoDomainException("Pedido com ID " + pedido.getId() + " não encontrado."));

        if (!pedido.getRestaurantId().equals(entity.getRestaurantId())) {
            restaurantRepository.findById(pedido.getRestaurantId())
                    .orElseThrow(() -> new PedidoDomainException("Restaurante com ID " + pedido.getRestaurantId() + " não encontrado."));
            entity.updateRestaurantId(pedido.getRestaurantId());
        }

        if (!pedido.getUserId().equals(entity.getUserId())) {
            userRepository.findById(pedido.getUserId())
                    .orElseThrow(() -> new PedidoDomainException("Usuário com ID " + pedido.getUserId() + " não encontrado."));
            entity.updateUserId(pedido.getUserId());
        }

        entity.updateDateTimeOrder(pedido.getDateTimeOrder());
        entity.updateDelivery(pedido.isDelivery());
        entity.updateDateTimeOrder(pedido.getDateTimeOrder());
        entity.updateLastModifiedDate();

       PedidoEntity updatedEntity = pedidoRepository.save(entity);
        return pedidoMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id)
                .map(pedidoMapper::toDomain);
    }
}
