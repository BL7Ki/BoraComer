package pos.java.bora_comer.infra.delivery.pedidoItem;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pos.java.bora_comer.core.usercase.pedidoItem.DeletePedidoItemUseCase;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeletePedidoItemController.class)
public class DeletePedidoItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeletePedidoItemUseCase deletePedidoItemUseCase;

    @Test
    void shouldDeletePedidoItemSuccessfully() throws Exception {
        Long pedidoItemId = 10L;

        // Mocka o comportamento do use case para não fazer nada (void)
        doNothing().when(deletePedidoItemUseCase).execute(pedidoItemId);

        mockMvc.perform(delete("/pedidoitems/{id}", pedidoItemId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica se o método execute do use case foi chamado exatamente 1 vez com o id correto
        Mockito.verify(deletePedidoItemUseCase, Mockito.times(1)).execute(pedidoItemId);
    }
}
