package pos.java.bora_comer.infra.gateway.orderItem.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemUpdateGateway;
import pos.java.bora_comer.core.mapper.orderItem.OrderItemMapper;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.orderItem.OrderItemRepository;
import pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity;

import java.util.Optional;

@Component
public class OrderItemUpdateGatewayImpl implements OrderItemUpdateGateway {

    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderItemUpdateGatewayImpl(OrderItemRepository orderItemRepository,
                                       OrderItemMapper orderItemMapper,
                                       OrderRepository orderRepository,
                                       MenuItemRepository menuItemRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Transactional
    @Override
    public OrderItem update(OrderItem orderItem) throws OrderItemDomainException {
        OrderItemEntity entity = orderItemRepository.findById(orderItem.getId())
                .orElseThrow(() -> new OrderItemDomainException("Item de Pedido com ID " + orderItem.getId() + " não encontrado."));

        if (!orderItem.getOrderId().equals(entity.getOrderId())) {
            orderRepository.findById(orderItem.getOrderId())
                    .orElseThrow(() -> new OrderItemDomainException("Pedido com ID " + orderItem.getOrderId() + " não encontrado."));
            entity.updateOrderId(orderItem.getOrderId());
        }

        if (!orderItem.getMenuItemId().equals(entity.getMenuItemId())) {
            menuItemRepository.findById(orderItem.getMenuItemId())
                    .orElseThrow(() -> new OrderItemDomainException("Item de Menu com ID " + orderItem.getMenuItemId() + " não encontrado."));
            entity.updateMenuItemId(orderItem.getMenuItemId());
        }

        entity.updateQuantity(orderItem.getQuantity());
        entity.updateLastModifiedDate();

       OrderItemEntity updatedEntity = orderItemRepository.save(entity);
        return orderItemMapper.toDomain(updatedEntity);
    }

    @Override
    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItemMapper::toDomain);
    }
}
