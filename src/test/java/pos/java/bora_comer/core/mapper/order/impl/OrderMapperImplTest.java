package pos.java.bora_comer.core.mapper.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.errors.OrderDomainException;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.order.entity.OrderEntity;

import static org.junit.jupiter.api.Assertions.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefault;

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
    @DisplayName("toDomain(PedidoRequestDTO) returns valid domain")
    void toDomain_FromRequestDTO_ShouldMapCorrectly() {
        var requestDTO = new OrderRequestDTO(
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );

        Order domain = mapper.toDomain(requestDTO);

        assertNotNull(domain);
        assertNull(domain.getId());
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(true, domain.isDelivery());
        assertEquals(1L, domain.getRestaurantId());
        assertTrue(domain.getUserId() == 1L);

    }

    @Test
    @DisplayName("toDomain(PedidoRequestDTO) throws exception on null")
    void toDomain_FromRequestDTO_ShouldThrowOnNull() {
        assertThrows(OrderDomainException.class, () -> mapper.toDomain((OrderRequestDTO) null));
    }

    @Test
    @DisplayName("toEntity(Pedido) returns valid entity")
    void toEntity_FromDomain_ShouldMapCorrectly() {
        Order domain = createDefault();

        var entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(dateTime, entity.getDateTimeOrder());
        assertEquals(true, entity.isDelivery());
        assertEquals(1L, entity.getUserId());
        assertTrue(entity.getRestaurantId() == 1L);
        
    }

    @Test
    @DisplayName("toEntity(Pedido) throws exception on null")
    void toEntity_FromDomain_ShouldThrowOnNull() {
        assertThrows(OrderDomainException.class, () -> mapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain(PedidoEntity) returns valid domain with ID")
    void toDomain_FromEntity_ShouldMapCorrectly() {
        var entity = OrderEntity.create(
                dateTime,
                true,
                1L,
                1L
        );

        var domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(true, domain.isDelivery());
        assertEquals(1L, domain.getUserId());
        assertTrue(domain.getRestaurantId() == 1L);
        assertEquals(entity.getId(), domain.getId());
    }

    @Test
    @DisplayName("toDomain(PedidoEntity) throws exception on null")
    void toDomain_FromEntity_ShouldThrowOnNull() {
        assertThrows(OrderDomainException.class, () -> mapper.toDomain((OrderEntity) null));
    }

    @Test
    @DisplayName("toResponseDTO(Pedido) returns valid DTO")
    void toResponseDTO_FromDomain_ShouldMapCorrectly() {
        var domain = Order.create(
                1L,
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );

        var dto = mapper.toResponseDTO(domain);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(dateTime, dto.dateTimeOrder());
        assertEquals(true, dto.delivery());
        assertEquals(1L, dto.userId());
        assertEquals(1L, dto.restaurantId());

    }

    @Test
    @DisplayName("toResponseDTO(Pedido) throws exception on null")
    void toResponseDTO_FromDomain_ShouldThrowOnNull() {
        assertThrows(OrderDomainException.class, () -> mapper.toResponseDTO(null));
    }

    @Test
    @DisplayName("toDomain(PedidoUpdateRequestDTO, Long) returns valid domain with ID")
    void toDomain_FromUpdateRequestDTO_ShouldMapCorrectly() {
        var updateDTO = new OrderUpdateRequestDTO(
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );
        Long id = 555L;

        Long restaurantID = 88L;
        var domain = mapper.toDomain(updateDTO, id, restaurantID, 1L);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(true, domain.isDelivery());
        assertEquals(restaurantID, domain.getRestaurantId());
        assertFalse(domain.getUserId() == null);
        
    }   
    

    @Test
    @DisplayName("toDomain(PedidoUpdateRequestDTO, Long, Long) returns valid domain with ID and restaurantId and userId")
    void toDomain_FromUpdateRequestDTOWithRestaurantId_ShouldMapCorrectly() {
        var updateDTO = new OrderUpdateRequestDTO(
                dateTime,
                true,
                88L,
                99L,
                dateTime
        );
        Long id = 555L;
        Long restaurantId = 88L;
        Long userId = 99L;

        var domain = mapper.toDomain(updateDTO, id, restaurantId, userId);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(true, domain.isDelivery());
        assertEquals(restaurantId, domain.getRestaurantId());
        assertEquals(userId, domain.getUserId());
    }

}
