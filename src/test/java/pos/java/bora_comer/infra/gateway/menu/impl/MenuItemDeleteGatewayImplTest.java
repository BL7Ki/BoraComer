package pos.java.bora_comer.infra.gateway.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;
import pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemDeleteGatewayImplTest {

    private MenuItemRepository menuItemRepository;
    private MenuItemDeleteGatewayImpl gateway;

    @BeforeEach
    void setup() {
        menuItemRepository = mock(MenuItemRepository.class);
        gateway = new MenuItemDeleteGatewayImpl(menuItemRepository);
    }

    @Test
    @DisplayName("Deve deletar o item do menu quando encontrado")
    void shouldDeleteMenuItemWhenFound() {
        Long menuItemId = 1L;
        MenuItemEntity entity = mock(MenuItemEntity.class);

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.of(entity));

        // Action
        assertDoesNotThrow(() -> gateway.deleteById(menuItemId));

        // Verify repository delete was called
        verify(menuItemRepository, times(1)).delete(entity);
        verify(menuItemRepository, times(1)).findById(menuItemId);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o item do menu não for encontrado")
    void shouldThrowExceptionWhenNotFound() {
        Long menuItemId = 2L;

        when(menuItemRepository.findById(menuItemId)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.deleteById(menuItemId));
        assertEquals("Item do menu com ID " + menuItemId + " não encontrado.", ex.getMessage());

        verify(menuItemRepository, times(1)).findById(menuItemId);
        verify(menuItemRepository, never()).delete(any());
    }
}
