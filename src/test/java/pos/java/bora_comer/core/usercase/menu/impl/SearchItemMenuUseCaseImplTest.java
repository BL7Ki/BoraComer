package pos.java.bora_comer.core.usercase.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.gateway.menu.MenuItemSearchGateway;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.MenuItemTestFactory.createDefaultWithId;

class SearchItemMenuUseCaseImplTest {

    private MenuItemSearchGateway menuItemSearchGateway;
    private SearchMenuItemUseCaseImpl searchMenuItemUseCase;

    @BeforeEach
    void setUp() {
        menuItemSearchGateway = Mockito.mock(MenuItemSearchGateway.class);
        searchMenuItemUseCase = new SearchMenuItemUseCaseImpl(menuItemSearchGateway);
    }

    @Test
    void findById_shouldReturnMenuItem() {
        // Arrange
        MenuItem mockMenuItem = createDefaultWithId();

        when(menuItemSearchGateway.findById(10L)).thenReturn(mockMenuItem);

        // Act
        MenuItem result = searchMenuItemUseCase.findById(10L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getName()).isEqualTo("Sushi");
        assertThat(result.getRestaurantId()).isEqualTo(1L);
        verify(menuItemSearchGateway, times(1)).findById(10L);
    }

    @Test
    void findAll_shouldReturnPageOfMenuItems() {
        // Arrange
        int page = 0;
        int size = 2;

        MenuItem menuItem1 = createDefaultWithId();
        MenuItem menuItem2 = createDefaultWithId();

        Page<MenuItem> mockPage = new PageImpl<>(List.of(menuItem1, menuItem2));
        when(menuItemSearchGateway.findAll(page, size)).thenReturn(mockPage);

        // Act
        Page<MenuItem> resultPage = searchMenuItemUseCase.findAll(page, size);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
        assertThat(resultPage.getContent()).extracting(MenuItem::getName)
                .containsExactly("Sushi", "Sushi");
        verify(menuItemSearchGateway, times(1)).findAll(page, size);
    }
}
