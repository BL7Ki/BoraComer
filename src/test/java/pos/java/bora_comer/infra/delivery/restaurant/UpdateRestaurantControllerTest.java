package pos.java.bora_comer.infra.delivery.restaurant;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.core.usercase.restaurant.UpdateRestaurantUseCase;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantResponseDTO;
import pos.java.bora_comer.infra.delivery.restaurant.dto.RestaurantUpdateRequestDTO;
import pos.java.bora_comer.infra.security.auth.CustomUserDetailsService;
import pos.java.bora_comer.infra.security.jwt.JwtUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pos.java.bora_comer.util.factory.RestaurantTestFactory.createUpdateRequestDTOWithId;

@WebMvcTest(
        controllers = UpdateRestaurantController.class,
        excludeAutoConfiguration = {
                SecurityAutoConfiguration.class
        }
)
public class UpdateRestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private RestaurantMapper restaurantMapper;

    @MockBean
    private UpdateRestaurantUseCase updateRestaurantUseCase;

    @Test
    void shouldUpdateRestaurantSuccessfully() throws Exception {
        Long id = 10L;
        Long ownerId = 2L;

        RestaurantUpdateRequestDTO updateRequestDTO = createUpdateRequestDTOWithId();

        Restaurant existingRestaurant = Restaurant.create(
                id,
                "Old Name",
                "Old Address",
                "Old Cuisine",
                "00:00 - 00:00",
                ownerId
        );

        Mockito.when(updateRestaurantUseCase.findById(id)).thenReturn(existingRestaurant);

        Restaurant domainRestaurant = Restaurant.create(
                id,
                updateRequestDTO.name(),
                updateRequestDTO.address(),
                updateRequestDTO.cuisineType(),
                updateRequestDTO.openingHours(),
                ownerId
        );

        Mockito.when(restaurantMapper.toDomain(updateRequestDTO, id, ownerId)).thenReturn(domainRestaurant);
        Mockito.when(updateRestaurantUseCase.execute(domainRestaurant)).thenReturn(domainRestaurant);

        RestaurantResponseDTO responseDTO = new RestaurantResponseDTO(
                id,
                updateRequestDTO.name(),
                updateRequestDTO.address(),
                updateRequestDTO.cuisineType(),
                updateRequestDTO.openingHours(),
                ownerId
        );

        Mockito.when(restaurantMapper.toResponseDTO(domainRestaurant)).thenReturn(responseDTO);

        mockMvc.perform(put("/restaurants/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value(updateRequestDTO.name()))
                .andExpect(jsonPath("$.endereco").value(updateRequestDTO.address()))
                .andExpect(jsonPath("$.tipo_cozinha").value(updateRequestDTO.cuisineType()))
                .andExpect(jsonPath("$.horario_funcionamento").value(updateRequestDTO.openingHours()))
                .andExpect(jsonPath("$.dono_id").value(ownerId.intValue()));
    }
}
