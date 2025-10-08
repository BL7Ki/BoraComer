package pos.java.bora_comer.core.mapper.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.errors.OrderItemDomainException;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemRequestDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.orderItem.entity.OrderItemEntity;

import static org.junit.jupiter.api.Assertions.*;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefault;

import java.time.LocalDateTime;

class OrderItemMapperImplTest {

    private OrderItemMapperImpl mapper;
    

    @BeforeEach
    void setUp() {
        mapper = new OrderItemMapperImpl();
    }

    @Test
    @DisplayName("toDomain(OrderRequestDTO) returns valid domain")
    void toDomain_FromRequestDTO_ShouldMapCorrectly() {
        var requestDTO = new OrderItemRequestDTO(
                1L,
                1L,
                2,
                LocalDateTime.parse("2024-10-10T12:00:00")
        );

        OrderItem domain = mapper.toDomain(requestDTO);

        assertNotNull(domain);
        assertNull(domain.getId());
        assertEquals(1L, domain.getOrderId());
        assertEquals(1L, domain.getMenuItemId());
        assertEquals(2, domain.getQuantity());
    }

    @Test
    @DisplayName("toDomain(OrderItemRequestDTO) throws exception on null")
    void toDomain_FromRequestDTO_ShouldThrowOnNull() {
        assertThrows(OrderItemDomainException.class, () -> mapper.toDomain((OrderItemRequestDTO) null));
    }

    @Test
    @DisplayName("toEntity(OrderItem) returns valid entity")
    void toEntity_FromDomain_ShouldMapCorrectly() {
        OrderItem domain = createDefault();

        var entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(1L, entity.getOrderId());
        assertEquals(1L, entity.getMenuItemId());
        assertEquals(2, entity.getQuantity());
    }

    @Test
    @DisplayName("toEntity(OrderItem) throws exception on null")
    void toEntity_FromDomain_ShouldThrowOnNull() {
        assertThrows(OrderItemDomainException.class, () -> mapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain(OrderItemEntity) returns valid domain with ID")
    void toDomain_FromEntity_ShouldMapCorrectly() {
        var entity = OrderItemEntity.create(
                1L,
                1L,
                2   
        );

        var domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getOrderId());
        assertEquals(1L, domain.getMenuItemId());
        assertEquals(2, domain.getQuantity());  
    }

    @Test
    @DisplayName("toDomain(OrderItemEntity) throws exception on null")
    void toDomain_FromEntity_ShouldThrowOnNull() {
        assertThrows(OrderItemDomainException.class, () -> mapper.toDomain((OrderItemEntity) null));
    }

    @Test
    @DisplayName("toResponseDTO(OrderItem) returns valid DTO")
    void toResponseDTO_FromDomain_ShouldMapCorrectly() {
        var domain = OrderItem.create(
                1L,
                1L,
                1L,
                2,
                LocalDateTime.parse("2024-10-10T12:00:00")
        );

        var dto = mapper.toResponseDTO(domain);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(1L, dto.orderId());
        assertEquals(1L, dto.menuItemId());
        assertEquals(2, dto.quantity());
        assertEquals(LocalDateTime.parse("2024-10-10T12:00:00"), dto.lastModifiedDate());
    }

    @Test
    @DisplayName("toResponseDTO(OrderItem) throws exception on null")
    void toResponseDTO_FromDomain_ShouldThrowOnNull() {
        assertThrows(OrderItemDomainException.class, () -> mapper.toResponseDTO(null));
    }

    @Test
    @DisplayName("toDomain(OrderItemUpdateRequestDTO, Long) returns valid domain with ID")
    void toDomain_FromUpdateRequestDTO_ShouldMapCorrectly() {
        var updateDTO = new OrderItemUpdateRequestDTO(
                1L,
                1L,
                2,
                LocalDateTime.parse("2024-10-10T12:00:00")
        );
        Long id = 555L;

        Long orderID = 88L;
        var domain = mapper.toDomain(updateDTO, id, orderID, 1L);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(1L, domain.getMenuItemId());
        assertEquals(2, domain.getQuantity());
        assertEquals(orderID, domain.getOrderId());
    }   
    

    @Test
    @DisplayName("toDomain(OrderItemUpdateRequestDTO, Long, Long) returns valid domain with ID and orderId and menuItemId")
    void toDomain_FromUpdateRequestDTOWitOrderId_ShouldMapCorrectly() {
        var updateDTO = new OrderItemUpdateRequestDTO(
                1L,
                1L,
                2,
                LocalDateTime.parse("2024-10-10T12:00:00")
        );
        Long id = 555L;
        Long orderId = 88L;
        Long menuItemId = 99L;

        var domain = mapper.toDomain(updateDTO, id, orderId, menuItemId);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(orderId, domain.getOrderId());
        assertEquals(menuItemId, domain.getMenuItemId());
        assertEquals(2, domain.getQuantity());
    }

}
