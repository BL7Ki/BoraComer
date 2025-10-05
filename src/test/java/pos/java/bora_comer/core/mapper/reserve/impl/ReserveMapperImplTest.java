package pos.java.bora_comer.core.mapper.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.errors.ReserveDomainException;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveRequestDTO;
import pos.java.bora_comer.infra.delivery.reserve.dto.ReserveUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.reserve.entity.ReserveEntity;

import static org.junit.jupiter.api.Assertions.*;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createDefault;

import java.time.LocalDateTime;

class ReserveMapperImplTest {

    private ReserveMapperImpl mapper;
    private static final String dateStr = "2024-10-10T12:00:00";
    private static final LocalDateTime dateTime = LocalDateTime.parse(dateStr);    

    @BeforeEach
    void setUp() {
        mapper = new ReserveMapperImpl();
    }

    @Test
    @DisplayName("toDomain(ReserveRequestDTO) returns valid domain")
    void toDomain_FromRequestDTO_ShouldMapCorrectly() {
        var requestDTO = new ReserveRequestDTO(
                dateTime,
                2,
                1L,
                1L,
                dateTime
        );

        Reserve domain = mapper.toDomain(requestDTO);

        assertNotNull(domain);
        assertNull(domain.getId());
        assertEquals(dateTime, domain.getDateTimeReserve());
        assertEquals(2, domain.getQuantity());
        assertEquals(1L, domain.getRestaurantId());
        assertTrue(domain.getUserId() == 1L);

    }

    @Test
    @DisplayName("toDomain(ReserveRequestDTO) throws exception on null")
    void toDomain_FromRequestDTO_ShouldThrowOnNull() {
        assertThrows(ReserveDomainException.class, () -> mapper.toDomain((ReserveRequestDTO) null));
    }

    @Test
    @DisplayName("toEntity(Reserve) returns valid entity")
    void toEntity_FromDomain_ShouldMapCorrectly() {
        Reserve domain = createDefault();

        var entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals(dateTime, entity.getDateTimeReserve());
        assertEquals(2, entity.getQuantity());
        assertEquals(1L, entity.getUserId());
        assertTrue(entity.getRestaurantId() == 1L);
        
    }

    @Test
    @DisplayName("toEntity(Reserve) throws exception on null")
    void toEntity_FromDomain_ShouldThrowOnNull() {
        assertThrows(ReserveDomainException.class, () -> mapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain(ReserveEntity) returns valid domain with ID")
    void toDomain_FromEntity_ShouldMapCorrectly() {
        var entity = ReserveEntity.create(
                dateTime,
                2,
                1L,
                1L
        );

        var domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(dateTime, domain.getDateTimeReserve());
        assertEquals(2, domain.getQuantity());
        assertEquals(1L, domain.getUserId());
        assertTrue(domain.getRestaurantId() == 1L);
        assertEquals(entity.getId(), domain.getId());
    }

    @Test
    @DisplayName("toDomain(ReserveEntity) throws exception on null")
    void toDomain_FromEntity_ShouldThrowOnNull() {
        assertThrows(ReserveDomainException.class, () -> mapper.toDomain((ReserveEntity) null));
    }

    @Test
    @DisplayName("toResponseDTO(Reserve) returns valid DTO")
    void toResponseDTO_FromDomain_ShouldMapCorrectly() {
        var domain = Reserve.create(
                1L,
                dateTime,
                2,
                1L,
                1L,
                dateTime
        );

        var dto = mapper.toResponseDTO(domain);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(dateTime, dto.dateTimeReserve());
        assertEquals(2, dto.quantity());
        assertEquals(1L, dto.userId());
        assertEquals(1L, dto.restaurantId());

    }

    @Test
    @DisplayName("toResponseDTO(Reserve) throws exception on null")
    void toResponseDTO_FromDomain_ShouldThrowOnNull() {
        assertThrows(ReserveDomainException.class, () -> mapper.toResponseDTO(null));
    }

    @Test
    @DisplayName("toDomain(ReserveUpdateRequestDTO, Long) returns valid domain with ID")
    void toDomain_FromUpdateRequestDTO_ShouldMapCorrectly() {
        var updateDTO = new ReserveUpdateRequestDTO(
                dateTime,
                2,
                1L,
                1L,
                dateTime
        );
        Long id = 555L;

        Long restaurantID = 88L;
        var domain = mapper.toDomain(updateDTO, id, restaurantID, 1L);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(dateTime, domain.getDateTimeReserve());
        assertEquals(2, domain.getQuantity());
        assertEquals(restaurantID, domain.getRestaurantId());
        assertFalse(domain.getUserId() == null);
        
    }   
    

    @Test
    @DisplayName("toDomain(ReserveUpdateRequestDTO, Long, Long) returns valid domain with ID and restaurantId and userId")
    void toDomain_FromUpdateRequestDTOWithRestaurantId_ShouldMapCorrectly() {
        var updateDTO = new ReserveUpdateRequestDTO(
                dateTime,
                2,
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
        assertEquals(dateTime, domain.getDateTimeReserve());
        assertEquals(2, domain.getQuantity());
        assertEquals(restaurantId, domain.getRestaurantId());
        assertEquals(userId, domain.getUserId());
    }

}
