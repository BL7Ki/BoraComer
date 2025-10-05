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
import pos.java.bora_comer.core.usercase.order.UpdateOrderUseCase;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createUpdateRequestDTO;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createResponseDTOWithId;
import static pos.java.bora_comer.util.factory.OrderTestFactory.createDefaultWithId;

@WebMvcTest(
        controllers = UpdateOrderController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
public class UpdateOrderControllerTest {

    private static final Long TEST_ID = 10L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private UpdateOrderUseCase updateOrderUseCase;

    @Test
    void shouldUpdateOrderSuccessfully() throws Exception {
        // Arrange: Preparação dos objetos de teste e mocks
        OrderUpdateRequestDTO requestDTO = createUpdateRequestDTO();

        Order updatedDomain = createDefaultWithId();
        updatedDomain = updatedDomain.applyUpdate(requestDTO);

        OrderResponseDTO responseDTO = createResponseDTOWithId();

        // Configuração dos Mocks (Foco no fluxo UseCase -> Mapper)
        when(updateOrderUseCase.execute(eq(TEST_ID), any(OrderUpdateRequestDTO.class)))
                .thenReturn(updatedDomain);

        when(orderMapper.toResponseDTO(eq(updatedDomain)))
                .thenReturn(responseDTO);

        // Act & Assert: Execução da requisição e validação
        mockMvc.perform(put("/orders/{id}", TEST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Esperamos status 200 OK
                .andExpect(jsonPath("$.id").value(responseDTO.id()))
                .andExpect(jsonPath("$.delivery").value(responseDTO.delivery()))
                .andExpect(jsonPath("$.restaurante_id").value(responseDTO.restaurantId()))
                .andExpect(jsonPath("$.usuario_id").value(responseDTO.userId()))
                .andExpect(jsonPath("$.status").value(responseDTO.status()));

        verify(updateOrderUseCase).execute(eq(TEST_ID), eq(requestDTO));
        verify(orderMapper).toResponseDTO(updatedDomain);
    }
}