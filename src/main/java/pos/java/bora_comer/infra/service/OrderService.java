package pos.java.bora_comer.infra.service;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.infra.persistence.repository.order.OrderRepository;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;
import pos.java.bora_comer.infra.rabbitmq.OrderEventProducer;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements pos.java.bora_comer.core.usercase.order.CreateOrderUseCase {

    private final OrderEventProducer orderEventProducer;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderEventProducer orderEventProducer,
                        OrderRepository orderRepository,
                        OrderMapper orderMapper) {
        this.orderEventProducer = orderEventProducer;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public Order execute(Order orderDomain) {

        OrderEntity entityToSave = orderMapper.toEntity(orderDomain);

        OrderEntity savedEntity = orderRepository.save(entityToSave);

        Order persistedOrder = orderMapper.toDomain(savedEntity);

        orderEventProducer.sendOrderCreatedEvent(persistedOrder);

        System.out.println("OrderService: Order created successfully and event sent.");

        return persistedOrder;
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    public List<Order> findAll() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Order finalize(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));

        orderEntity.updateStatus("FINALIZED");

        OrderEntity finalizedEntity = orderRepository.save(orderEntity);

        return orderMapper.toDomain(finalizedEntity);
    }
}