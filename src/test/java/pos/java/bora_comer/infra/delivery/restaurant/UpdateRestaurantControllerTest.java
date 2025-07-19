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
import pos.java.bora_comer.core.usercase.restaurant.UpdateRestaurantUseCase;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.RestaurantTestFactory.createUpdateRequestDTOWithId;

@WebMvcTest(UpdateRestaurantController.class)
public class UpdateRestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestaurantMapper restaurantMapper;

    @MockBean
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Test
    void shouldUpdateRestaurantSuccessfully() throws Exception {
        Long id = 10L;

        RestaurantUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        // Mock domain object after mapping
        Restaurant domainRestaurant = Restaurant.create(
                id,
                updateRequestDTO.name(),
                updateRequestDTO.address(),
                updateRequestDTO.cuisineType(),
                updateRequestDTO.openingHours(),
                updateRequestDTO.ownerId()
        );

        // Mock returned updated restaurant
        Restaurant updatedRestaurant = Restaurant.create(
                id,
                updateRequestDTO.name(),
                updateRequestDTO.address(),
                updateRequestDTO.cuisineType(),
                updateRequestDTO.openingHours(),
                updateRequestDTO.ownerId()
        );

        // Mock the response DTO
        RestaurantResponseDTO responseDTO = new RestaurantResponseDTO(
                id,
                updateRequestDTO.name(),
                updateRequestDTO.address(),
                updateRequestDTO.cuisineType(),
                updateRequestDTO.openingHours(),
                updateRequestDTO.ownerId()
        );

        Mockito.when(restaurantMapper.toDomain(updateRequestDTO, id)).thenReturn(domainRestaurant);
        Mockito.when(updateRestaurantUseCase.execute(domainRestaurant)).thenReturn(updatedRestaurant);
        Mockito.when(restaurantMapper.toResponseDTO(updatedRestaurant)).thenReturn(responseDTO);

        mockMvc.perform(put("/restaurants/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Updated Name"))
                .andExpect(jsonPath("$.endereco").value("Updated Address"))
                .andExpect(jsonPath("$.tipo_cozinha").value("Updated Cuisine"))
                .andExpect(jsonPath("$.horario_funcionamento").value("09:00 - 21:00"))
                .andExpect(jsonPath("$.dono_id").value(2));
    }
}
