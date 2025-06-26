package pos.java.bora_comer.infra.delivery.user;

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
import pos.java.bora_comer.core.domain.LoginResponseEnum;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.CustomExceptionHandler;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.UppdateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pos.java.bora_comer.util.IntegrationTestUtil;

@ExtendWith(MockitoExtension.class)
class UppdateUserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UppdateUserUseCase updateUserUseCase;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UppdateUserController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new CustomExceptionHandler())
                .build();
    }

    @Test
    void deveAtualizarUsuarioComSucesso() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_user_sucess.json");
        UserUpdateRequestDTO requestDTO = objectMapper.readValue(requestJson, UserUpdateRequestDTO.class);

        User user = User.create("Novo Nome", "novo@email.com", "novouser", "senha", null, null, "2024-06-24");
        User updatedUser = User.create(1L, "Novo Nome", "novo@email.com", "novouser", "senha", null, null, "2024-06-24");
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "Novo Nome", "novo@email.com", "novouser", null, null, "2024-06-24");

        when(userMapper.toDomain(requestDTO, 1L)).thenReturn(user);
        when(updateUserUseCase.execute(user)).thenReturn(updatedUser);
        when(userMapper.toResponseDTO(updatedUser)).thenReturn(responseDTO);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Novo Nome"));
    }

    @Test
    void deveRetornarErroQuandoUsuarioNaoEncontrado() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_user_sucess.json");
        UserUpdateRequestDTO requestDTO = objectMapper.readValue(requestJson, UserUpdateRequestDTO.class);

        User user = User.create("Nome", "email@email.com", "usuario", "senha", null, null, "2024-06-24");

        when(userMapper.toDomain(requestDTO, 30L)).thenReturn(user);
        when(updateUserUseCase.execute(user))
                .thenThrow(new UserDomainException("User with ID 30 not found"));

        mockMvc.perform(put("/users/30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("User with ID 30 not found"))
                .andExpect(jsonPath("$.path").value("/users/30"));
    }

    @Test
    void deveTrocarSenhaComSucesso() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_password_sucess.json");

        mockMvc.perform(put("/users/1/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(LoginResponseEnum.PASSWORD_CHANGED_SUCCESSFULLY.getMessage()));
    }

    @Test
    void deveRetornarErroQuandoSenhaAtualIncorreta() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_password_error.json");

        doThrow(new UserDomainException("Senha atual incorreta."))
                .when(updateUserUseCase).changeUserPassword(eq(1L), eq("senhaErrada"), eq("senhaNova"));

        mockMvc.perform(put("/users/1/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarErroDeValidacaoQuandoNovaSenhaNaoInformada() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_password_null.json");

        mockMvc.perform(put("/users/1/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarErroDeValidacaoQuandoNovaSenhaVazia() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_password_empty.json");

        mockMvc.perform(put("/users/1/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}
