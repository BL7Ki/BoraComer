package pos.java.bora_comer.core.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

public class Order {

    private final Long id;
    private final String clienteId;
    private final OrderStatusEnum status;
    private final LocalDateTime dataCriacao;
    private final BigDecimal valorTotal;

    public Order(Long id, String clienteId, OrderStatusEnum status, LocalDateTime dataCriacao, BigDecimal valorTotal) {
        this.id = id;
        this.clienteId = clienteId;
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.valorTotal = valorTotal;
    }

    public static Order create(String clienteId, BigDecimal valorTotal) {
        // Gera o ID e a data de criação internamente, garantindo um estado inicial consistente.
        long orderId = new Random().nextLong();
        return new Order(
                orderId,
                clienteId,
                OrderStatusEnum.CREATED,
                LocalDateTime.now(),
                valorTotal
        );
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}

