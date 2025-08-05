package pos.java.bora_comer.core.usercase.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.gateway.menu.MenuItemDeleteGateway;

import static org.mockito.Mockito.*;

class DeleteMenuItemUseCaseImplTest {

    private MenuItemDeleteGateway menuItemDeleteGateway;
    private DeleteMenuItemUseCaseImpl deleteMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemDeleteGateway = Mockito.mock(MenuItemDeleteGateway.class);
        deleteMenuItemUseCase = new DeleteMenuItemUseCaseImpl(menuItemDeleteGateway);
    }

    @Test
    void execute_shouldCallDeleteByIdOnce() {
        // Arrange
        Long menuItemId = 5L;

        // Act
        deleteMenuItemUseCase.execute(menuItemId);

        // Assert
        verify(menuItemDeleteGateway, times(1)).deleteById(menuItemId);
    }
}
