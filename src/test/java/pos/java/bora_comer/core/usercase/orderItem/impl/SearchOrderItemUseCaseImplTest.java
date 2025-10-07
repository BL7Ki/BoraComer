package pos.java.bora_comer.core.usercase.orderItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import pos.java.bora_comer.core.domain.orderItem.OrderItem;
import pos.java.bora_comer.core.gateway.orderItem.OrderItemSearchGateway;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.OrderItemTestFactory.createDefaultWithId;

class SearchOrderItemUseCaseImplTest {

    private OrderItemSearchGateway orderItemSearchGateway;
    private SearchOrderItemUseCaseImpl searchOrderItemUseCase;

    @BeforeEach
    void setUp() {
        orderItemSearchGateway = Mockito.mock(OrderItemSearchGateway.class);
        searchOrderItemUseCase = new SearchOrderItemUseCaseImpl(orderItemSearchGateway);
    }

    @Test
    void findById_shouldReturnOrderItem() {
        // Arrange
        OrderItem mockOrderItem = createDefaultWithId();

        when(orderItemSearchGateway.findById(10L)).thenReturn(mockOrderItem);

        // Act
        OrderItem result = searchOrderItemUseCase.findById(10L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getMenuItemId()).isEqualTo(1L);
        assertThat(result.getOrderId()).isEqualTo(1L);
        verify(orderItemSearchGateway, times(1)).findById(10L);
    }

    @Test
    void findAll_shouldReturnPageOfOrderItems() {
        // Arrange
        int page = 0;
        int size = 2;

        OrderItem orderItem1 = createDefaultWithId();
        OrderItem orderItem2 = createDefaultWithId();

        Page<OrderItem> mockPage = new PageImpl<>(List.of(orderItem1, orderItem2));
        when(orderItemSearchGateway.findAll(page, size)).thenReturn(mockPage);

        // Act
        Page<OrderItem> resultPage = searchOrderItemUseCase.findAll(page, size);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
        verify(orderItemSearchGateway, times(1)).findAll(page, size);
    }
}
