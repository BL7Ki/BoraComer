package pos.java.bora_comer.infra.delivery.orderItem;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.usercase.orderItem.DeleteOrderItemUseCase;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = DeleteOrderItemController.class,
        excludeAutoConfiguration = {SecurityAutoConfiguration.class}
)
public class DeleteOrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteOrderItemUseCase deleteOrderItemUseCase;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void shouldDeleteOrderItemSuccessfully() throws Exception {
        Long orderItemId = 10L;

        // Mocka o comportamento do use case para não fazer nada (void)
        doNothing().when(deleteOrderItemUseCase).execute(orderItemId);

        mockMvc.perform(delete("/orderitems/{id}", orderItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica se o método execute do use case foi chamado exatamente 1 vez com o id correto
        Mockito.verify(deleteOrderItemUseCase, Mockito.times(1)).execute(orderItemId);
    }
}
