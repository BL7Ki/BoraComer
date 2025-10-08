package pos.java.bora_comer.infra.gateway.order.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.core.gateway.order.OrderUpdateGateway;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

import java.util.Optional;

@Component
public class OrderUpdateGatewayImpl implements OrderUpdateGateway {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public OrderUpdateGatewayImpl(OrderRepository orderRepository,
                                   OrderMapper orderMapper,
                                   RestaurantRepository restaurantRepository,
                                   UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public Order update(Order order) throws OrderDomainException {
        OrderEntity entity = orderRepository.findById(order.getId())
                .orElseThrow(() -> new OrderDomainException("Pedido com ID " + order.getId() + " não encontrado."));

        if (!order.getRestaurantId().equals(entity.getRestaurantId())) {
            restaurantRepository.findById(order.getRestaurantId())
                    .orElseThrow(() -> new OrderDomainException("Restaurante com ID " + order.getRestaurantId() + " não encontrado."));
            entity.updateRestaurantId(order.getRestaurantId());
        }

        if (!order.getUserId().equals(entity.getUserId())) {
            userRepository.findById(order.getUserId())
                    .orElseThrow(() -> new OrderDomainException("Usuário com ID " + order.getUserId() + " não encontrado."));
            entity.updateUserId(order.getUserId());
        }

        entity.updateDateTimeOrder(order.getDateTimeOrder());
        entity.updateDelivery(order.isDelivery());
        entity.updateDateTimeOrder(order.getDateTimeOrder());
        entity.updateLastModifiedDate();

       OrderEntity updatedEntity = orderRepository.save(entity);
        return orderMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDomain);
    }
}
