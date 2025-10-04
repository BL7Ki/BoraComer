package pos.java.bora_comer.core.usercase.reserve.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import pos.java.bora_comer.core.domain.reserve.Reserve;
import pos.java.bora_comer.core.gateway.reserve.ReserveSearchGateway;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pos.java.bora_comer.util.factory.ReserveTestFactory.createDefaultWithId;

class SearchReserveUseCaseImplTest {

    private ReserveSearchGateway reserveSearchGateway;
    private SearchReserveUseCaseImpl searchReserveUseCase;

    @BeforeEach
    void setUp() {
        reserveSearchGateway = Mockito.mock(ReserveSearchGateway.class);
        searchReserveUseCase = new SearchReserveUseCaseImpl(reserveSearchGateway);
    }

    @Test
    void findById_shouldReturnReserve() {
        // Arrange
        Reserve mockReserve = createDefaultWithId();

        when(reserveSearchGateway.findById(10L)).thenReturn(mockReserve);

        // Act
        Reserve result = searchReserveUseCase.findById(10L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getRestaurantId()).isEqualTo(1L);
        assertThat(result.getQuantity()).isEqualTo(2);
        assertThat(result.getDateTimeReserve()).isNotNull();
        verify(reserveSearchGateway, times(1)).findById(10L);
    }

    @Test
    void findAll_shouldReturnPageOfReserves() {
        // Arrange
        int page = 0;
        int size = 2;

        Reserve reserve1 = createDefaultWithId();
        Reserve reserve2 = createDefaultWithId();

        Page<Reserve> mockPage = new PageImpl<>(List.of(reserve1, reserve2));
        when(reserveSearchGateway.findAll(page, size)).thenReturn(mockPage);

        // Act
        Page<Reserve> resultPage = searchReserveUseCase.findAll(page, size);

        // Assert
        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getContent()).hasSize(2);
        verify(reserveSearchGateway, times(1)).findAll(page, size);
    }
}
