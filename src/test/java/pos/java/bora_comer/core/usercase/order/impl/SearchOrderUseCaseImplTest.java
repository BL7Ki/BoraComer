package pos.java.bora_comer.core.usercase.order.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.gateway.order.OrderSearchGateway;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;

class SearchOrderUseCaseImplTest {

    private OrderSearchGateway orderSearchGateway;
    private SearchOrderUseCaseImpl searchOrderUseCase;

    @BeforeEach
    void setUp() {
        orderSearchGateway = Mockito.mock(OrderSearchGateway.class);
        searchOrderUseCase = new SearchOrderUseCaseImpl(orderSearchGateway);
    }

    @Test
    void findById_shouldReturnOrder() {
        // Arrange
        Order mockOrder = createDefaultWithId();

        when(orderSearchGateway.findById(10L)).thenReturn(mockOrder);

        // Act
        Order result = searchOrderUseCase.findById(10L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getRestaurantId()).isEqualTo(1L);
        verify(orderSearchGateway, times(1)).findById(10L);
    }

    @Test
    void findAll_shouldReturnPageOfOrders() {
        // Arrange
        int page = 0;
        int size = 2;

        Order order1 = createDefaultWithId();
        Order order2 = createDefaultWithId();

        Page<Order> mockPage = new PageImpl<>(List.of(order1, order2));
        when(orderSearchGateway.findAll(page, size)).thenReturn(mockPage);

        // Act
        Page<Order> resultPage = searchOrderUseCase.findAll(page, size);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
        verify(orderSearchGateway, times(1)).findAll(page, size);
    }
}
