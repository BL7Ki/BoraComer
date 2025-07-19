package pos.java.bora_comer.infra.delivery.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.core.usercase.restaurant.CreateRestaurantUseCase;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantRequestDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.util.RestaurantTestFactory;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.RestaurantTestFactory.*;

@WebMvcTest(CreateRestaurantController.class)
class CreateRestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantMapper restaurantMapper;

    @MockBean
    private CreateRestaurantUseCase createRestaurantUseCase;

    @Test
    void shouldCreateRestaurantSuccessfully() throws Exception {
        // given (entrada do cliente)
        RestaurantRequestDTO requestDTO = createRequestDTOWithId();

        Restaurant domain = createDefaultWithId();

        RestaurantResponseDTO responseDTO = createResponseDTOWithId();

        // mocks
        Mockito.when(restaurantMapper.toDomain(any(RestaurantRequestDTO.class))).thenReturn(domain);
        Mockito.when(createRestaurantUseCase.execute(any(Restaurant.class))).thenReturn(domain);
        Mockito.when(restaurantMapper.toResponseDTO(any(Restaurant.class))).thenReturn(responseDTO);

        // when & then
        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/restaurants/10"))
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nome").value("Restaurante Japa"))
                .andExpect(jsonPath("$.endereco").value("Rua B, 456"))
                .andExpect(jsonPath("$.tipo_cozinha").value("Japonesa"))
                .andExpect(jsonPath("$.horario_funcionamento").value("11:00 - 23:00"))
                .andExpect(jsonPath("$.dono_id").value(55L));


        // verificação de chamadas
        Mockito.verify(restaurantMapper).toDomain(requestDTO);
        Mockito.verify(createRestaurantUseCase).execute(domain);
        Mockito.verify(restaurantMapper).toResponseDTO(domain);
    }
}
