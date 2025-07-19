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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        Restaurant mockRestaurant = Restaurant.create(
                id,
                "Sushi Place",
                "Rua das Flores, 123",
                "Japonesa",
                "10:00 - 22:00",
                1L
        );

        RestaurantResponseDTO responseDTO = new RestaurantResponseDTO(
                id,
                "Sushi Place",
                "Rua das Flores, 123",
                "Japonesa",
                "10:00 - 22:00",
                1L
        );

        Mockito.when(searchRestaurantUseCase.findById(id)).thenReturn(mockRestaurant);
        Mockito.when(restaurantMapper.toResponseDTO(mockRestaurant)).thenReturn(responseDTO);

        mockMvc.perform(get("/restaurants/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Sushi Place"))
                .andExpect(jsonPath("$.endereco").value("Rua das Flores, 123"))
                .andExpect(jsonPath("$.tipo_cozinha").value("Japonesa"))
                .andExpect(jsonPath("$.horario_funcionamento").value("10:00 - 22:00"))
                .andExpect(jsonPath("$.dono_id").value(1));
    }

    @Test
    void shouldFindAllRestaurantsSuccessfully() throws Exception {
        int page = 0;
        int size = 2;

        Restaurant restaurant1 = Restaurant.create(
                1L,
                "Sushi Place",
                "Rua das Flores, 123",
                "Japonesa",
                "10:00 - 22:00",
                1L
        );

        Restaurant restaurant2 = Restaurant.create(
                2L,
                "Pizza House",
                "Av. Paulista, 1000",
                "Italiana",
                "11:00 - 23:00",
                2L
        );

        Page<Restaurant> pageResult = new PageImpl<>(List.of(restaurant1, restaurant2), PageRequest.of(page, size), 2);

        RestaurantResponseDTO dto1 = new RestaurantResponseDTO(
                1L,
                "Sushi Place",
                "Rua das Flores, 123",
                "Japonesa",
                "10:00 - 22:00",
                1L
        );

        RestaurantResponseDTO dto2 = new RestaurantResponseDTO(
                2L,
                "Pizza House",
                "Av. Paulista, 1000",
                "Italiana",
                "11:00 - 23:00",
                2L
        );

        Mockito.when(searchRestaurantUseCase.findAll(page, size)).thenReturn(pageResult);
        Mockito.when(restaurantMapper.toResponseDTO(restaurant1)).thenReturn(dto1);
        Mockito.when(restaurantMapper.toResponseDTO(restaurant2)).thenReturn(dto2);

        mockMvc.perform(get("/restaurants")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Sushi Place"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Pizza House"));
    }
}
