package pos.java.bora_comer.core.mapper.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.domain.order.OrderStatusEnum;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO; // Importe o DTO de Update
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

import static org.junit.jupiter.api.Assertions.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class OrderMapperImplTest {

    private OrderMapperImpl mapper;
    private static final String dateStr = "2024-10-10T12:00:00";
    private static final LocalDateTime dateTime = LocalDateTime.parse(dateStr);

    @BeforeEach
    void setUp() {
        mapper = new OrderMapperImpl();
    }

    @Test
    @DisplayName("toDomain(OrderRequestDTO) retorna domínio válido (Criação)")
    void toDomain_FromRequestDTO_ShouldMapCorrectly() {
        var requestDTO = new OrderRequestDTO(
                dateTime,
                true,
                1L,
                1L
        );

        Order domain = mapper.toDomain(requestDTO);

        assertNotNull(domain);
        assertNull(domain.getId());
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertTrue(domain.isDelivery());
        assertEquals(1L, domain.getRestaurantId());
        assertEquals(1L, domain.getUserId());
        assertEquals(OrderStatusEnum.CREATED, domain.getStatus());
        assertEquals(BigDecimal.ZERO, domain.getValorTotal());
    }

    @Test
    @DisplayName("toDomain(OrderRequestDTO) lança exceção em nulo")
    void toDomain_FromRequestDTO_ShouldThrowOnNull() {
        assertThrows(OrderDomainException.class, () -> mapper.toDomain((OrderRequestDTO) null));
    }

    @Test
    @DisplayName("toEntity(Order) retorna entidade válida")
    void toEntity_FromDomain_ShouldMapCorrectly() {
        Order domain = createDefaultWithId();

        var entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(domain.getId(), entity.getId());
        assertEquals(domain.getDateTimeOrder(), entity.getDateTimeOrder());
        assertEquals(domain.isDelivery(), entity.isDelivery());
        assertEquals(domain.getUserId(), entity.getUserId());
        assertEquals(domain.getRestaurantId(), entity.getRestaurantId());
        assertEquals(domain.getStatus(), entity.getStatus());
        assertEquals(domain.getValorTotal(), entity.getValorTotal());
        assertNotNull(entity.getLastModifiedDate());
    }

    @Test
    @DisplayName("toEntity(Order) lança exceção em nulo")
    void toEntity_FromDomain_ShouldThrowOnNull() {
        assertThrows(OrderDomainException.class, () -> mapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain(OrderEntity) retorna domínio válido com ID")
    void toDomain_FromEntity_ShouldMapCorrectly() {
        var entity = OrderEntity.create(
                1L,
                dateTime,
                true,
                1L,
                1L,
                OrderStatusEnum.CREATED,
                BigDecimal.ZERO,
                dateTime
        );

        var domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getDateTimeOrder(), domain.getDateTimeOrder());
        assertEquals(entity.isDelivery(), domain.isDelivery());
        assertEquals(entity.getUserId(), domain.getUserId());
        assertEquals(entity.getRestaurantId(), domain.getRestaurantId());
        assertEquals(entity.getStatus(), domain.getStatus());
        assertEquals(entity.getValorTotal(), domain.getValorTotal());
        assertEquals(entity.getLastModifiedDate(), domain.getLastModifiedDate());
    }

    @Test
    @DisplayName("toDomain(OrderEntity) lança exceção em nulo")
    void toDomain_FromEntity_ShouldThrowOnNull() {
        assertThrows(OrderDomainException.class, () -> mapper.toDomain((OrderEntity) null));
    }

    @Test
    @DisplayName("toResponseDTO(Order) retorna DTO válido")
    void toResponseDTO_FromDomain_ShouldMapCorrectly() {
        var domain = createDefaultWithId();

        var dto = mapper.toResponseDTO(domain);

        assertNotNull(dto);
        assertEquals(domain.getId(), dto.id());
        assertEquals(domain.getDateTimeOrder(), dto.dateTimeOrder());
        assertEquals(domain.isDelivery(), dto.delivery());
        assertEquals(domain.getUserId(), dto.userId());
        assertEquals(domain.getRestaurantId(), dto.restaurantId());
        assertEquals(domain.getStatus().name(), dto.status());
        assertEquals(domain.getLastModifiedDate(), dto.lastModifiedDate());
    }

    @Test
    @DisplayName("toResponseDTO(Order) lança exceção em nulo")
    void toResponseDTO_FromDomain_ShouldThrowOnNull() {
        assertThrows(OrderDomainException.class, () -> mapper.toResponseDTO(null));
    }

    // --- Novos testes da Feature-EvertonFase3 ---

    @Test
    @DisplayName("toDomain(OrderUpdateRequestDTO, Long) retorna domínio válido com ID")
    void toDomain_FromUpdateRequestDTO_ShouldMapCorrectly() {
        // Criando uma OrderUpdateRequestDTO (usando valores concretos para mapeamento)
        var updateDTO = new OrderUpdateRequestDTO(
                dateTime,
                true,
                1L,
                1L,
                OrderStatusEnum.IN_PROGRESS.name() // Corrigido para o campo 'status'
        );
        Long id = 555L;
        Long restaurantID = 88L;
        Long userId = 1L; // Usando o userId esperado

        var domain = mapper.toDomain(updateDTO, id, restaurantID, userId);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(true, domain.isDelivery());
        assertEquals(restaurantID, domain.getRestaurantId());
        assertEquals(userId, domain.getUserId());
        assertEquals(OrderStatusEnum.IN_PROGRESS, domain.getStatus()); // Verifica status
        
        // Verifica que o valor total é inicializado (se o mapeador fizer isso)
        assertNotNull(domain.getValorTotal()); 
    } 

    @Test
    @DisplayName("toDomain(OrderUpdateRequestDTO, Long, Long) retorna domínio válido com todos os IDs")
    void toDomain_FromUpdateRequestDTOWithRestaurantId_ShouldMapCorrectly() {
        // Criando uma OrderUpdateRequestDTO
        var updateDTO = new OrderUpdateRequestDTO(
                dateTime.plusHours(1),
                false,
                88L,
                99L,
                OrderStatusEnum.CANCELED.name()
        );
        Long id = 555L;
        Long restaurantId = 88L;
        Long userId = 99L;

        var domain = mapper.toDomain(updateDTO, id, restaurantId, userId);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(dateTime.plusHours(1), domain.getDateTimeOrder());
        assertEquals(false, domain.isDelivery());
        assertEquals(restaurantId, domain.getRestaurantId());
        assertEquals(userId, domain.getUserId());
        assertEquals(OrderStatusEnum.CANCELED, domain.getStatus());
    }
}