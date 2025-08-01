package pos.java.bora_comer.core.usercase.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.gateway.menu.MenuItemUpdateGateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createDefaultWithId;

class UpdateMenuItemUseCaseImplTest {

    private MenuItemUpdateGateway menuItemUpdateGateway;
    private UpdateMenuItemUseCaseImpl updateMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemUpdateGateway = Mockito.mock(MenuItemUpdateGateway.class);
        updateMenuItemUseCase = new UpdateMenuItemUseCaseImpl(menuItemUpdateGateway);
    }

    @Test
    void execute_shouldUpdateAndReturnMenuItem() {
        // Arrange
        MenuItem menuItemToUpdate = createDefaultWithId();

        when(menuItemUpdateGateway.update(menuItemToUpdate)).thenReturn(menuItemToUpdate);

        // Act
        MenuItem updatedMenuItem = updateMenuItemUseCase.execute(menuItemToUpdate);

        // Assert
        assertThat(updatedMenuItem).isNotNull();
        assertThat(updatedMenuItem.getName()).isEqualTo("Sushi");
        verify(menuItemUpdateGateway, times(1)).update(menuItemToUpdate);
    }
}
