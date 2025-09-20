package pos.java.bora_comer.infra.persistence.repository.order.entity;

import jakarta.persistence.*;
import pos.java.bora_comer.core.domain.order.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pedidos")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cliente_id", nullable = false)
    private String clienteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatusEnum status;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    // Construtor padrão exigido pelo JPA
    public OrderEntity() {
    }

    // Construtor privado para a criação da entidade
    private OrderEntity(String clienteId, BigDecimal valorTotal) {
        this.clienteId = clienteId;
        this.status = OrderStatusEnum.CREATED;
        this.dataCriacao = LocalDateTime.now();
        this.valorTotal = valorTotal;
    }

    // fabric para novas instancias
    public static OrderEntity create(String clienteId, BigDecimal valorTotal) {
        return new OrderEntity(clienteId, valorTotal);
    }

    // fabric pra fazer mapper
    public static OrderEntity of(Long id, String clienteId, OrderStatusEnum status, LocalDateTime dataCriacao, BigDecimal valorTotal) {
        OrderEntity order = new OrderEntity();
        order.id = id;
        order.clienteId = clienteId;
        order.status = status;
        order.dataCriacao = dataCriacao;
        order.valorTotal = valorTotal;
        return order;
    }

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

    // pra atualizar o status sem setter
    public void updateStatus(OrderStatusEnum newStatus) {
        this.status = newStatus;
    }
}

