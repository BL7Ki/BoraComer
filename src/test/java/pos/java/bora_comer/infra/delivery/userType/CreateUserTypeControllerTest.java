package pos.java.bora_comer.infra.delivery.userType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.CustomExceptionHandler;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.core.usercase.userType.CreateUserTypeUseCase;
import pos.java.bora_comer.util.factory.UserTypeFactory;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class CreateUserTypeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserTypeMapper userTypeMapper;

    @Mock
    private CreateUserTypeUseCase createUserTypeUseCase;

    @InjectMocks
    private CreateUserTypeController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void deveCriarUserTypeComSucesso() throws Exception {

        CreateUserTypeRequestDTO requestDTO = UserTypeFactory.createUserTypeRequestDTO();
        String requestJson = objectMapper.writeValueAsString(requestDTO);

        UserType userTypeDomain = UserTypeFactory.createUserType();
        UserType userTypeSalvo = UserTypeFactory.createUserType();
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO(1L, "ADMIN");

        when(userTypeMapper.toDomain(requestDTO.name())).thenReturn(userTypeDomain);

        when(createUserTypeUseCase.execute(userTypeDomain)).thenReturn(userTypeSalvo);
        when(userTypeMapper.toResponse(userTypeSalvo)).thenReturn(responseDTO);

        mockMvc.perform(post("/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/user-types/1"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.tipo_usuario").value("ADMIN"));
    }

    @Test
    void deveRetornarErroQuandoUserTypeJaExiste() throws Exception {
        CreateUserTypeRequestDTO requestDTO = UserTypeFactory.createUserTypeRequestDTO();
        String requestJson = objectMapper.writeValueAsString(requestDTO);

        UserType userTypeDomain = UserTypeFactory.createUserType();

        when(userTypeMapper.toDomain(requestDTO.name())).thenReturn(userTypeDomain);
        when(createUserTypeUseCase.execute(userTypeDomain))
                .thenThrow(new UserDomainException("User type already exists with name: ADMIN"));

        mockMvc.perform(post("/user-types")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("User type already exists with name: ADMIN"))
                .andExpect(jsonPath("$.path").value("/user-types"));
    }

}