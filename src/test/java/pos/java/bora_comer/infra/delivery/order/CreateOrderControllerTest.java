package pos.java.bora_comer.infra.delivery.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.domain.order.Order;
import pos.java.bora_comer.core.mapper.order.OrderMapper;
import pos.java.bora_comer.core.usercase.order.CreateOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createResponseDTOWithId;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createRequestDTO;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;

@WebMvcTest(
        controllers = CreateOrderController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
class CreateOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private CreateOrderUseCase createOrderUseCase;

    @Test
    void shouldCreateOrderSuccessfully() throws Exception {
        // Arrange
        OrderRequestDTO requestDTO = createRequestDTO();
        Order domainResult = createDefaultWithId();
        OrderResponseDTO responseDTO = createResponseDTOWithId();

        // Mocks
        when(orderMapper.toDomain(any(OrderRequestDTO.class))).thenReturn(domainResult);
        when(createOrderUseCase.execute(any(Order.class))).thenReturn(domainResult);
        when(orderMapper.toResponseDTO(any(Order.class))).thenReturn(responseDTO);

        // Act & Assert
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/orders/10"))
                .andExpect(jsonPath("$.id").value(responseDTO.id()))
                .andExpect(jsonPath("$.usuario_id").value(responseDTO.userId()))
                .andExpect(jsonPath("$.restaurante_id").value(responseDTO.restaurantId()))
                .andExpect(jsonPath("$.delivery").value(responseDTO.delivery()))
                .andExpect(jsonPath("$.status").value(responseDTO.status()))
                .andExpect(jsonPath("$.data_hora").exists());


        verify(orderMapper).toDomain(requestDTO);
        verify(createOrderUseCase).execute(domainResult);
        verify(orderMapper).toResponseDTO(domainResult);
    }
}