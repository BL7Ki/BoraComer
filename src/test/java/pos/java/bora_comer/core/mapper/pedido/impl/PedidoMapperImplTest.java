package pos.java.bora_comer.core.mapper.pedido.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.pedido.Pedido;
import pos.java.bora_comer.core.errors.PedidoDomainException;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoRequestDTO;
import pos.java.bora_comer.infra.delivery.pedido.dto.PedidoUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.pedido.entity.PedidoEntity;
import static org.junit.jupiter.api.Assertions.*;
import static pos.java.bora_comer.util.factory.PedidoTestFactory.createDefault;

class PedidoMapperImplTest {

    private PedidoMapperImpl mapper;
    private static final String dateStr = "2024-10-10T12:00:00";
    private static final java.time.LocalDateTime dateTime = java.time.LocalDateTime.parse(dateStr);

    @BeforeEach
    void setUp() {
        mapper = new PedidoMapperImpl();
    }

    @Test
    @DisplayName("toDomain(PedidoRequestDTO) returns valid domain")
    void toDomain_FromRequestDTO_ShouldMapCorrectly() {
        var requestDTO = new PedidoRequestDTO(
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );

        Pedido domain = mapper.toDomain(requestDTO);

        assertNotNull(domain);
        assertNull(domain.getId());
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(true, domain.isDelivery());
        assertEquals(1L, domain.getRestaurantId());
        assertTrue(domain.getUserId() == 1L);
        assertEquals(dateTime, domain.getLastModifiedDate());
    }

    @Test
    @DisplayName("toDomain(PedidoRequestDTO) throws exception on null")
    void toDomain_FromRequestDTO_ShouldThrowOnNull() {
        assertThrows(PedidoDomainException.class, () -> mapper.toDomain((PedidoRequestDTO) null));
    }

    @Test
    @DisplayName("toEntity(Pedido) returns valid entity")
    void toEntity_FromDomain_ShouldMapCorrectly() {
        Pedido domain = createDefault();

        var entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(dateTime, entity.getDateTimeOrder());
        assertEquals(true, entity.isDelivery());
        assertEquals(1L, entity.getRestaurantId());
        assertTrue(entity.getUserId() == 1L);
    }

    @Test
    @DisplayName("toEntity(Pedido) throws exception on null")
    void toEntity_FromDomain_ShouldThrowOnNull() {
        assertThrows(PedidoDomainException.class, () -> mapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain(PedidoEntity) returns valid domain with ID")
    void toDomain_FromEntity_ShouldMapCorrectly() {
        var entity = PedidoEntity.create(
                dateTime,
                true,
                1L,
                1L
        );

        var domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(true, domain.isDelivery());
        assertTrue(domain.getRestaurantId() == 1L);
        assertEquals(1L, domain.getUserId());
    }

    @Test
    @DisplayName("toDomain(PedidoEntity) throws exception on null")
    void toDomain_FromEntity_ShouldThrowOnNull() {
        assertThrows(PedidoDomainException.class, () -> mapper.toDomain((PedidoEntity) null));
    }

    @Test
    @DisplayName("toResponseDTO(Pedido) returns valid DTO")
    void toResponseDTO_FromDomain_ShouldMapCorrectly() {
        var domain = Pedido.create(
                10L,
                dateTime,
                true,
                1L,
                1L,
                dateTime
        );

        var dto = mapper.toResponseDTO(domain);

        assertNotNull(dto);
        assertEquals(10L, dto.id()); 
        assertEquals(dateTime, dto.dateTimeOrder());
        assertEquals(true, dto.delivery());
        assertTrue( dto.restaurantId() == 1L);
        assertEquals(1L, dto.userId());
        assertEquals(dateTime, dto.lastModifiedDate());
    }

    @Test
    @DisplayName("toResponseDTO(Pedido) throws exception on null")
    void toResponseDTO_FromDomain_ShouldThrowOnNull() {
        assertThrows(PedidoDomainException.class, () -> mapper.toResponseDTO(null));
    }

    @Test
    @DisplayName("toDomain(PedidoUpdateRequestDTO, Long, Long) returns valid domain with ID")
    void toDomain_FromUpdateRequestDTO_ShouldMapCorrectly() {
        var updateDTO = new PedidoUpdateRequestDTO(
                dateTime,
                false,
                88L,
                99L,
                dateTime
        );
        Long id = 555L;

        Long restaurantID = 88L;
        Long userID = 99L;
        var domain = mapper.toDomain(updateDTO, id, restaurantID, userID);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(false, domain.isDelivery());
        assertEquals(88L, domain.getRestaurantId());
        assertEquals(99L, domain.getUserId());
        assertEquals(dateTime, domain.getLastModifiedDate());
    }  
    

    @Test
    @DisplayName("toDomain(PedidoUpdateRequestDTO, Long, Long, Long) returns valid domain with ID and restaurantId and userId")
    void toDomain_FromUpdateRequestDTOWithOwnerId_ShouldMapCorrectly() {
        var updateDTO = new PedidoUpdateRequestDTO(
                dateTime,
                false,
                88L,
                99L,
                dateTime
        );
        Long id = 555L;
        Long restaurantId = 88L;
        Long userID = 99L;
        var domain = mapper.toDomain(updateDTO, id, restaurantId, userID);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(dateTime, domain.getDateTimeOrder());
        assertEquals(false, domain.isDelivery());
        assertEquals(restaurantId, domain.getRestaurantId());
        assertEquals(userID, domain.getUserId());
        assertEquals(dateTime, domain.getLastModifiedDate());

    }

}
