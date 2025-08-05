package pos.java.bora_comer.infra.delivery.restaurant;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.java.bora_comer.core.usercase.restaurant.DeleteRestaurantUseCase;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeleteRestaurantController.class)
public class DeleteRestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeleteRestaurantUseCase deleteRestaurantUseCase;

    @Test
    void shouldDeleteRestaurantSuccessfully() throws Exception {
        Long restaurantId = 10L;

        // Mocka o comportamento do use case para não fazer nada (void)
        doNothing().when(deleteRestaurantUseCase).execute(restaurantId);

        mockMvc.perform(delete("/restaurants/{id}", restaurantId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verifica se o método execute do use case foi chamado exatamente 1 vez com o id correto
        Mockito.verify(deleteRestaurantUseCase, Mockito.times(1)).execute(restaurantId);
    }
}
