package pos.java.bora_comer.infra.gateway.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;
import pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemUpdateGatewayImplTest {

    private MenuItemRepository menuItemRepository;
    private MenuItemMapper menuItemMapper;
    private RestaurantRepository restaurantRepository;
    private MenuItemUpdateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        menuItemRepository = mock(MenuItemRepository.class);
        menuItemMapper = mock(MenuItemMapper.class);
        restaurantRepository = mock(RestaurantRepository.class);

        gateway = new MenuItemUpdateGatewayImpl(menuItemRepository, menuItemMapper, restaurantRepository);
    }

    @Test
    @DisplayName("Deve atualizar um item do menu com sucesso quando o restaurante não for alterado")
    void shouldUpdateMenuItemWhenRestaurantIdNotChanged() {
        Long id = 1L;
        Long restaurantId = 10L;
        MenuItem domainMenuItem = MenuItem.create(id, 
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                restaurantId);

        MenuItemEntity entity = mock(MenuItemEntity.class);
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(restaurantId);

        MenuItemEntity updatedEntity = mock(MenuItemEntity.class);
        when(menuItemRepository.save(entity)).thenReturn(updatedEntity);

        MenuItem expectedDomain = mock(MenuItem.class);
        when(menuItemMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        MenuItem result = gateway.update(domainMenuItem);

        verify(entity).updateName("Sushi");
        verify(entity).updateDescription("Sushi de salmão com arroz");
        verify(entity).updatePrice(BigDecimal.valueOf(29.99));
        verify(entity).updateInPlaceOnly(true);
        verify(entity).updateImagePath("sushi.jpg");
        verify(entity).updateLastModifiedDate();
        
        verify(menuItemRepository).save(entity);
        verify(menuItemMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve atualizar um item do menu e alterar o restaurante quando o ID do restaurante for alterado")
    void shouldUpdateMenuItemAndChangeRestaurant() {
        Long id = 1L;
        Long oldRestaurantId = 10L;
        Long newRestaurantId = 20L;

        MenuItem domainMenuItem = MenuItem.create(id, 
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                newRestaurantId);

        MenuItemEntity entity = mock(MenuItemEntity.class);
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(oldRestaurantId);

        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.of(mock()));

        MenuItemEntity updatedEntity = mock(MenuItemEntity.class);
        when(menuItemRepository.save(entity)).thenReturn(updatedEntity);

        MenuItem expectedDomain = mock(MenuItem.class);
        when(menuItemMapper.toDomain(updatedEntity)).thenReturn(expectedDomain);

        MenuItem result = gateway.update(domainMenuItem);

        verify(restaurantRepository).findById(newRestaurantId);
        verify(entity).updateRestaurantId(newRestaurantId);

        verify(entity).updateName("Sushi");
        verify(entity).updateDescription("Sushi de salmão com arroz");      
        verify(entity).updatePrice(BigDecimal.valueOf(29.99));
        verify(entity).updateInPlaceOnly(true);
        verify(entity).updateImagePath("sushi.jpg");
        verify(entity).updateLastModifiedDate();

        verify(menuItemRepository).save(entity);
        verify(menuItemMapper).toDomain(updatedEntity);

        assertSame(expectedDomain, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item do menu não for encontrado")
    void shouldThrowWhenMenuItemNotFound() {
        Long id = 1L;
        MenuItem domainMenuItem = MenuItem.create(id, 
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                10L);

        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        MenuItemDomainException ex = assertThrows(MenuItemDomainException.class, () -> gateway.update(domainMenuItem));
        assertEquals("Item do menu com ID " + id + " não encontrado.", ex.getMessage());

        verify(menuItemRepository).findById(id);
        verifyNoMoreInteractions(menuItemRepository, restaurantRepository, menuItemMapper);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o novo restaurante não for encontrado")
    void shouldThrowWhenNewRestaurantNotFound() {
        Long id = 1L;
        Long oldRestaurantId = 10L;
        Long newRestaurantId = 20L;
        MenuItem domainMenuItem = MenuItem.create(
                id,
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                newRestaurantId
        );

        MenuItemEntity entity = mock(MenuItemEntity.class);
        when(menuItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(entity.getRestaurantId()).thenReturn(oldRestaurantId);

        when(restaurantRepository.findById(newRestaurantId)).thenReturn(Optional.empty());

        MenuItemDomainException ex = assertThrows(MenuItemDomainException.class, () -> gateway.update(domainMenuItem));
        assertEquals("Restaurante com ID " + newRestaurantId + " não encontrado.", ex.getMessage());

        verify(menuItemRepository).findById(id);
        verify(restaurantRepository).findById(newRestaurantId);

        verifyNoMoreInteractions(menuItemRepository, restaurantRepository, menuItemMapper);
    }
}
