package pos.java.bora_comer.core.mapper.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity;
import static org.junit.jupiter.api.Assertions.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createDefault;

import java.math.BigDecimal;

class MenuItemMapperImplTest {

    private MenuItemMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new MenuItemMapperImpl();
    }

    @Test
    @DisplayName("toDomain(MenuItemRequestDTO) returns valid domain")
    void toDomain_FromRequestDTO_ShouldMapCorrectly() {
        var requestDTO = new MenuItemRequestDTO(
                    "Sushi",
                    "Sushi de salmão com arroz",
                    BigDecimal.valueOf(29.99),
                    true,
                    "sushi.jpg",
                    1L
        );

        MenuItem domain = mapper.toDomain(requestDTO);

        assertNotNull(domain);
        assertNull(domain.getId());
        assertEquals("Sushi", domain.getName());
        assertEquals("Sushi de salmão com arroz", domain.getDescription());
        assertEquals(BigDecimal.valueOf(29.99), domain.getPrice());
        assertTrue(domain.isInPlaceOnly());
        assertEquals("sushi.jpg", domain.getImagePath());
        assertEquals(1L, domain.getRestaurantId());

    }

    @Test
    @DisplayName("toDomain(MenuItemRequestDTO) throws exception on null")
    void toDomain_FromRequestDTO_ShouldThrowOnNull() {
        assertThrows(MenuItemDomainException.class, () -> mapper.toDomain((MenuItemRequestDTO) null));
    }

    @Test
    @DisplayName("toEntity(MenuItem) returns valid entity")
    void toEntity_FromDomain_ShouldMapCorrectly() {
        MenuItem domain = createDefault();

        var entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertEquals("Sushi", entity.getName());
        assertEquals("Sushi de salmão com arroz", entity.getDescription());
        assertEquals(BigDecimal.valueOf(29.99), entity.getPrice());
        assertTrue(entity.isInPlaceOnly());
        assertEquals("sushi.jpg", entity.getImagePath());
        assertEquals(1L, entity.getRestaurantId());
    }

    @Test
    @DisplayName("toEntity(MenuItem) throws exception on null")
    void toEntity_FromDomain_ShouldThrowOnNull() {
        assertThrows(MenuItemDomainException.class, () -> mapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain(MenuItemEntity) returns valid domain with ID")
    void toDomain_FromEntity_ShouldMapCorrectly() {
        var entity = MenuItemEntity.create(
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                1L
        );

        var domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals("Sushi", domain.getName());
        assertEquals("Sushi de salmão com arroz", domain.getDescription());
        assertEquals(BigDecimal.valueOf(29.99), domain.getPrice());
        assertTrue(domain.isInPlaceOnly());
        assertEquals("sushi.jpg", domain.getImagePath());
        assertEquals(1L, domain.getRestaurantId());
    }

    @Test
    @DisplayName("toDomain(MenuItemEntity) throws exception on null")
    void toDomain_FromEntity_ShouldThrowOnNull() {
        assertThrows(MenuItemDomainException.class, () -> mapper.toDomain((MenuItemEntity) null));
    }

    @Test
    @DisplayName("toResponseDTO(MenuItem) returns valid DTO")
    void toResponseDTO_FromDomain_ShouldMapCorrectly() {
        var domain = MenuItem.create(
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                1L
        );

        var dto = mapper.toResponseDTO(domain);

        assertNotNull(dto);
        assertEquals("Sushi", dto.name());
        assertEquals("Sushi de salmão com arroz", dto.description());
        assertEquals(BigDecimal.valueOf(29.99), dto.price());
        assertTrue(dto.inPlaceOnly());
        assertEquals("sushi.jpg", dto.imagePath());
        assertEquals(1L, dto.restaurantId());
    }

    @Test
    @DisplayName("toResponseDTO(MenuItem) throws exception on null")
    void toResponseDTO_FromDomain_ShouldThrowOnNull() {
        assertThrows(MenuItemDomainException.class, () -> mapper.toResponseDTO(null));
    }

    @Test
    @DisplayName("toDomain(MenuItemUpdateRequestDTO, Long) returns valid domain with ID")
    void toDomain_FromUpdateRequestDTO_ShouldMapCorrectly() {
        var updateDTO = new MenuItemUpdateRequestDTO(
                "Sushi Updated",
                "Sushi de atum com arroz",
                BigDecimal.valueOf(34.99),
                false,
                "sushi_updated.jpg",
                88L
        );
        Long id = 555L;

        Long restaurantID = 88L;
        var domain = mapper.toDomain(updateDTO, id, restaurantID);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals("Sushi Updated", domain.getName());
        assertEquals("Sushi de atum com arroz", domain.getDescription());
        assertEquals(BigDecimal.valueOf(34.99), domain.getPrice());
        assertFalse(domain.isInPlaceOnly());
        assertEquals("sushi_updated.jpg", domain.getImagePath());
        assertEquals(restaurantID, domain.getRestaurantId());
    }   
    

    @Test
    @DisplayName("toDomain(MenuItemUpdateRequestDTO, Long, Long) returns valid domain with ID and ownerId")
    void toDomain_FromUpdateRequestDTOWithOwnerId_ShouldMapCorrectly() {
        var updateDTO = new MenuItemUpdateRequestDTO(
                "Sushi Updated",
                "Sushi de atum com arroz",
                BigDecimal.valueOf(34.99),
                false,
                "sushi_updated.jpg",
                88L
        );
        Long id = 555L;
        Long restaurantId = 88L;

        var domain = mapper.toDomain(updateDTO, id, restaurantId);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals("Sushi Updated", domain.getName());
        assertEquals("Sushi de atum com arroz", domain.getDescription());
        assertEquals(BigDecimal.valueOf(34.99), domain.getPrice());
        assertFalse(domain.isInPlaceOnly());
        assertEquals("sushi_updated.jpg", domain.getImagePath());
        assertEquals(restaurantId, domain.getRestaurantId());
    }

}
