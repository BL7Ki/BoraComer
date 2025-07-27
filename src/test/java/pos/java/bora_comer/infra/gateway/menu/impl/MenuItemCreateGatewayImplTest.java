package pos.java.bora_comer.infra.gateway.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;
import pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createDefault;

class MenuItemCreateGatewayImplTest {

    private MenuItemRepository menuItemRepository;
    private MenuItemMapper menuItemMapper;
    private MenuItemCreateGatewayImpl gateway;

    @BeforeEach
    void setup() {
        menuItemRepository = mock(MenuItemRepository.class);
        menuItemMapper = mock(MenuItemMapper.class);
        gateway = new MenuItemCreateGatewayImpl(menuItemRepository, menuItemMapper);
    }

    @Test
    @DisplayName("Deve lançar uma exceção se o nome do item do menu já existir no restaurante")
    void shouldThrowExceptionIfNameAndRestaurantIdExists() {
        MenuItem menuItem = createDefault();

        when(menuItemRepository.existsByNameAndRestaurantId(menuItem.getName(),menuItem.getRestaurantId())).thenReturn(true);

        MenuItemDomainException ex = assertThrows(MenuItemDomainException.class, () -> gateway.save(menuItem));
        assertEquals("Já existe um restaurante com esse nome.", ex.getMessage());

        verify(menuItemRepository, times(1)).existsByNameAndRestaurantId(menuItem.getName(), menuItem.getRestaurantId());
        verifyNoMoreInteractions(menuItemRepository);
        verifyNoInteractions(menuItemMapper);
    }

    @Test
    @DisplayName("Deve salvar e retornar o item do menu")
    void shouldSaveAndReturnMenuItem() {
        MenuItem menuItem = createDefault();
        MenuItemEntity entityToSave = new MenuItemEntity();
        MenuItemEntity savedEntity = new MenuItemEntity();
        MenuItem domainFromSaved = createDefault();

        when(menuItemRepository.existsByNameAndRestaurantId(menuItem.getName(),menuItem.getRestaurantId())).thenReturn(false);
        when(menuItemMapper.toEntity(menuItem)).thenReturn(entityToSave);
        when(menuItemRepository.save(entityToSave)).thenReturn(savedEntity);
        when(menuItemMapper.toDomain(savedEntity)).thenReturn(domainFromSaved);

        MenuItem result = gateway.save(menuItem);

        assertNotNull(result);
        assertEquals(domainFromSaved.getId(), result.getId());
        assertEquals(domainFromSaved.getName(), result.getName());

        verify(menuItemRepository, times(1)).existsByNameAndRestaurantId(menuItem.getName(),menuItem.getRestaurantId());
        verify(menuItemMapper, times(1)).toEntity(menuItem);
        verify(menuItemRepository, times(1)).save(entityToSave);
        verify(menuItemMapper, times(1)).toDomain(savedEntity);
    }
}
