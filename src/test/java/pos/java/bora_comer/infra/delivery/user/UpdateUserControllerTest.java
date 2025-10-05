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
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.CustomExceptionHandler;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.UpdateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pos.java.bora_comer.util.IntegrationTestUtil;
import pos.java.bora_comer.util.factory.UserTestFactory;

@ExtendWith(MockitoExtension.class)
class UpdateUserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UpdateUserController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String TEST_USERNAME = "teste.user";

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

        // Usuário original (baseado no ID 1L)
        User user = UserTestFactory.umUserComId(1L);

        // Usuário atualizado, usando a factory que já está ajustada para User.reconstruct
        User updatedUser = UserTestFactory.umUserComIdParaUpdate(1L);


        // DTO de resposta esperado (usado para o Mockito)
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "Novo Nome", "novo@email.com", "novouser", null, null, "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700", updatedUser.getUserTypeNameEnum().name()); // Usando o Enum do objeto Domain

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

        User user = UserTestFactory.umUserComId(20L);

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

        mockMvc.perform(put("/users/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Senha alterada com sucesso."));
    }

    @Test
    void deveRetornarErroQuandoSenhaAtualIncorreta() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_password_error.json");

        doThrow(new UserDomainException("Senha atual incorreta."))
                .when(updateUserUseCase).changeUserPassword(eq(TEST_USERNAME), eq("senhaErrada"), eq("senhaNova"));

        mockMvc.perform(put("/users/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarErroDeValidacaoQuandoNovaSenhaNaoInformada() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_password_null.json");

        mockMvc.perform(put("/users/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarErroDeValidacaoQuandoNovaSenhaVazia() throws Exception {
        String requestJson = IntegrationTestUtil.fromJsonPath("/json/delivery/user/request_update_password_empty.json");

        mockMvc.perform(put("/users/change-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveAssociarTipoUsuarioComSucesso() throws Exception {
        mockMvc.perform(put("/users/1/tipo-usuario/2"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornarErroQuandoUsuarioNaoEncontradoNaAssociacao() throws Exception {
        doThrow(new UserDomainException("Usuário não encontrado."))
                .when(updateUserUseCase).userAssociate(1L, 2L);

        mockMvc.perform(put("/users/1/tipo-usuario/2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Usuário não encontrado."));
    }

    @Test
    void deveRetornarErroQuandoTipoUsuarioNaoEncontradoNaAssociacao() throws Exception {
        doThrow(new UserDomainException("Tipo de usuário não encontrado"))
                .when(updateUserUseCase).userAssociate(1L, 99L);

        mockMvc.perform(put("/users/1/tipo-usuario/99"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Tipo de usuário não encontrado"));
    }
}