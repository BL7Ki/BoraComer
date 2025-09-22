package pos.java.bora_comer.infra.delivery.pedido;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.usercase.pedido.DeletePedidoUseCase;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeletePedidoController.class)
public class DeletePedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeletePedidoUseCase deletePedidoUseCase;

    @Test
    void shouldDeletePedidoSuccessfully() throws Exception {
        Long pedidoId = 10L;

        // Mocka o comportamento do use case para não fazer nada (void)
        doNothing().when(deletePedidoUseCase).execute(pedidoId);

        mockMvc.perform(delete("/pedidos/{id}", pedidoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica se o método execute do use case foi chamado exatamente 1 vez com o id correto
        Mockito.verify(deletePedidoUseCase, Mockito.times(1)).execute(pedidoId);
    }
}
