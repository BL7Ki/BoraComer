package pos.java.bora_comer.core.mapper.restaurant.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.errors.RestaurantDomainException;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantRequestDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;

import static org.junit.jupiter.api.Assertions.*;
import static pos.java.bora_comer.util.factory.RestaurantTestFactory.createDefault;

class RestaurantMapperImplTest {

    private RestaurantMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new RestaurantMapperImpl();
    }

    @Test
    @DisplayName("toDomain(RestaurantRequestDTO) returns valid domain")
    void toDomain_FromRequestDTO_ShouldMapCorrectly() {
        var requestDTO = new RestaurantRequestDTO(
                "Restaurante X",
                "Rua A, 123",
                "Italiana",
                "10:00 - 22:00",
                42L
        );

        Restaurant domain = mapper.toDomain(requestDTO);

        assertNotNull(domain);
        assertNull(domain.getId());
        assertEquals("Restaurante X", domain.getName());
        assertEquals("Rua A, 123", domain.getAddress());
        assertEquals("Italiana", domain.getCuisineType());
        assertEquals("10:00 - 22:00", domain.getOpeningHours());
        assertEquals(42L, domain.getOwnerId());
    }

    @Test
    @DisplayName("toDomain(RestaurantRequestDTO) throws exception on null")
    void toDomain_FromRequestDTO_ShouldThrowOnNull() {
        assertThrows(RestaurantDomainException.class, () -> mapper.toDomain((RestaurantRequestDTO) null));
    }

    @Test
    @DisplayName("toEntity(Restaurant) returns valid entity")
    void toEntity_FromDomain_ShouldMapCorrectly() {
        Restaurant domain = createDefault();

        var entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals("Restaurante Japa", entity.getName());
        assertEquals("Rua B, 456", entity.getAddress());
        assertEquals("Japonesa", entity.getCuisineType());
        assertEquals("11:00 - 23:00", entity.getOpeningHours());
        assertEquals(55L, entity.getOwnerId());
    }

    @Test
    @DisplayName("toEntity(Restaurant) throws exception on null")
    void toEntity_FromDomain_ShouldThrowOnNull() {
        assertThrows(RestaurantDomainException.class, () -> mapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain(RestaurantEntity) returns valid domain with ID")
    void toDomain_FromEntity_ShouldMapCorrectly() {
        var entity = RestaurantEntity.create(
                "Restaurante Z",
                "Rua C, 789",
                "Mexicana",
                "09:00 - 21:00",
                99L
        );

        var domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals("Restaurante Z", domain.getName());
        assertEquals("Rua C, 789", domain.getAddress());
        assertEquals("Mexicana", domain.getCuisineType());
        assertEquals("09:00 - 21:00", domain.getOpeningHours());
        assertEquals(99L, domain.getOwnerId());
    }

    @Test
    @DisplayName("toDomain(RestaurantEntity) throws exception on null")
    void toDomain_FromEntity_ShouldThrowOnNull() {
        assertThrows(RestaurantDomainException.class, () -> mapper.toDomain((RestaurantEntity) null));
    }

    @Test
    @DisplayName("toResponseDTO(Restaurant) returns valid DTO")
    void toResponseDTO_FromDomain_ShouldMapCorrectly() {
        var domain = Restaurant.create(
                200L,
                "Restaurante W",
                "Rua D, 321",
                "Francesa",
                "08:00 - 20:00",
                123L
        );

        var dto = mapper.toResponseDTO(domain);

        assertNotNull(dto);
        assertEquals(200L, dto.id());
        assertEquals("Restaurante W", dto.name());
        assertEquals("Rua D, 321", dto.address());
        assertEquals("Francesa", dto.cuisineType());
        assertEquals("08:00 - 20:00", dto.openingHours());
        assertEquals(123L, dto.ownerId());
    }

    @Test
    @DisplayName("toResponseDTO(Restaurant) throws exception on null")
    void toResponseDTO_FromDomain_ShouldThrowOnNull() {
        assertThrows(RestaurantDomainException.class, () -> mapper.toResponseDTO(null));
    }

    @Test
    @DisplayName("toDomain(RestaurantUpdateRequestDTO, Long) returns valid domain with ID")
    void toDomain_FromUpdateRequestDTO_ShouldMapCorrectly() {
        var updateDTO = new RestaurantUpdateRequestDTO(
                "Restaurante Updated",
                "Rua E, 654",
                "Brasileira",
                "07:00 - 19:00",
                88L
        );
        Long id = 555L;

        var domain = mapper.toDomain(updateDTO, id);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals("Restaurante Updated", domain.getName());
        assertEquals("Rua E, 654", domain.getAddress());
        assertEquals("Brasileira", domain.getCuisineType());
        assertEquals("07:00 - 19:00", domain.getOpeningHours());
        assertEquals(88L, domain.getOwnerId());
    }

    @Test
    @DisplayName("toDomain(RestaurantUpdateRequestDTO, Long) throws exception on null")
    void toDomain_FromUpdateRequestDTO_ShouldThrowOnNull() {
        assertThrows(RestaurantDomainException.class, () -> mapper.toDomain(null, 1L));
    }
}
