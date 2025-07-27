package pos.java.bora_comer.core.usercase.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.gateway.menu.MenuItemCreateGateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createDefault;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createDefaultWithId;

class CreateMenuItemUseCaseImplTest {

    private MenuItemCreateGateway menuItemCreateGateway;
    private CreateMenuItemUseCaseImpl createMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemCreateGateway = Mockito.mock(MenuItemCreateGateway.class);
        createMenuItemUseCase = new CreateMenuItemUseCaseImpl(menuItemCreateGateway);
    }

    @Test
    void execute_shouldReturnCreatedMenuItem() {
        // Arrange
        MenuItem menuItemToSave = createDefault();

        MenuItem savedMenuItem = createDefaultWithId();

        when(menuItemCreateGateway.save(any(MenuItem.class))).thenReturn(savedMenuItem);

        // Act
        MenuItem result = createMenuItemUseCase.execute(menuItemToSave);

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("Sushi", result.getName());
        verify(menuItemCreateGateway, times(1)).save(menuItemToSave);
    }
}
