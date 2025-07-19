package pos.java.bora_comer.infra.delivery.restaurant;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.core.usercase.restaurant.SearchRestaurantUseCase;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.RestaurantTestFactory.createDefaultWithId;
import static pos.java.bora_comer.util.factory.RestaurantTestFactory.createResponseDTOWithId;

@WebMvcTest(SearchRestaurantController.class)
public class SearchRestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchRestaurantUseCase searchRestaurantUseCase;

    @MockBean
    private RestaurantMapper restaurantMapper;

    @Test
    void shouldFindRestaurantByIdSuccessfully() throws Exception {
        Long id = 10L;

        Restaurant mockRestaurant = createDefaultWithId();

        RestaurantResponseDTO responseDTO = createResponseDTOWithId();

        Mockito.when(searchRestaurantUseCase.findById(id)).thenReturn(mockRestaurant);
        Mockito.when(restaurantMapper.toResponseDTO(mockRestaurant)).thenReturn(responseDTO);

        mockMvc.perform(get("/restaurants/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nome").value("Restaurante Japa"))
                .andExpect(jsonPath("$.endereco").value("Rua B, 456"))
                .andExpect(jsonPath("$.tipo_cozinha").value("Japonesa"))
                .andExpect(jsonPath("$.horario_funcionamento").value("11:00 - 23:00"))
                .andExpect(jsonPath("$.dono_id").value(55L));
    }

    @Test
    void shouldFindAllRestaurantsSuccessfully() throws Exception {
        int page = 0;
        int size = 2;

        Restaurant restaurant1 = createDefaultWithId();

        Restaurant restaurant2 = createDefaultWithId();

        Page<Restaurant> pageResult = new PageImpl<>(List.of(restaurant1, restaurant2), PageRequest.of(page, size), 2);

        RestaurantResponseDTO dto1 = createResponseDTOWithId();

        RestaurantResponseDTO dto2 = createResponseDTOWithId();

        Mockito.when(searchRestaurantUseCase.findAll(page, size)).thenReturn(pageResult);
        Mockito.when(restaurantMapper.toResponseDTO(restaurant1)).thenReturn(dto1);
        Mockito.when(restaurantMapper.toResponseDTO(restaurant2)).thenReturn(dto2);

        mockMvc.perform(get("/restaurants")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(10L))
                .andExpect(jsonPath("$[0].nome").value("Restaurante Japa"))
                .andExpect(jsonPath("$[1].id").value(10L))
                .andExpect(jsonPath("$[1].nome").value("Restaurante Japa"));
    }
}
