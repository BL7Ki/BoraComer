package pos.java.bora_comer.infra.delivery.userType;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.userType.UserTypeMapper;
import pos.java.bora_comer.core.usercase.userType.CreateUserTypeUseCase;
import pos.java.bora_comer.util.factory.UserTypeFactory;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeResponseDTO;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = CreateUserTypeController.class)
@AutoConfigureMockMvc(addFilters = false)

class CreateUserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTypeMapper userTypeMapper;

    @MockBean
    private CreateUserTypeUseCase createUserTypeUseCase;

    @InjectMocks
    private CreateUserTypeController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private String autorizationHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc3RvcnJlc2RvaXMiLCJyb2xlIjoiQ0xJRU5URSIsImlhdCI6MTc1OTg2MDc4MCwiZXhwIjoxNzU5ODgyMzgwfQ.LOFMI7Hp6cBtzS5avcR8fXPnwxVuxsl0wG2vUqrZGqo";

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
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
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
                        .with(user("usuario_jwt_teste").roles("ADMIN"))
                        .with(csrf())
                        .header("Authorization", autorizationHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("User type already exists with name: ADMIN"))
                .andExpect(jsonPath("$.path").value("/user-types"));
    }

}