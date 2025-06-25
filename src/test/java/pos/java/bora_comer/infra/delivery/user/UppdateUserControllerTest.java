package pos.java.bora_comer.infra.delivery.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.UppdateUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.AddressRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.AddressResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.infra.delivery.user.dto.UserUpdateRequestDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UppdateUserControllerTest {

    @Mock
    private UppdateUserUseCase updateUserUseCase;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UppdateUserController controller;

    @Test
    void deveriaAtualizarUsuarioERetornar200() {
        // Arrange: DTO de entrada
        AddressRequestDTO addressRequest = new AddressRequestDTO(
                "Rua Nova", "456", "São Paulo", "SP", "06543-210"
        );

        UserUpdateRequestDTO updateRequestDTO = new UserUpdateRequestDTO(
                "Leo Messi Atualizado",
                "messi_novo@email.com",
                "messinew",
                addressRequest // so pega nome, email, senha e endereco
        );

        AddressResponseDTO addressResponse = new AddressResponseDTO(
                "Rua Nova", "456", "São Paulo", "SP", "06543-210"
        );

        // Mock de Address
        Address address = mock(Address.class);

        // User esperado após mapper e update
        User domainUser = User.create(
                1L,
                "Leo Messi Atualizado",
                "messi_novo@email.com",
                "messinew",
                "novaSenha",
                address,
                UserRoleEnum.CLIENTE,
                "2024-06-21T15:00:00"
        );

        UserResponseDTO responseDTO = new UserResponseDTO(
                1L,
                "Leo Messi Atualizado",
                "messi_novo@email.com",
                "messinew",
                addressResponse,
                "CLIENTE",
                "2024-06-21T15:00:00"
        );

        // Configura mocks
        when(userMapper.toDomain(updateRequestDTO, 1L)).thenReturn(domainUser);
        when(updateUserUseCase.execute(domainUser)).thenReturn(domainUser);
        when(userMapper.toResponseDTO(domainUser)).thenReturn(responseDTO);

        // Act: executa o método da controller
        ResponseEntity<UserResponseDTO> response = controller.update(1L, updateRequestDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(responseDTO, response.getBody());

        // Verifica interações
        verify(userMapper).toDomain(updateRequestDTO, 1L);
        verify(updateUserUseCase).execute(domainUser);
        verify(userMapper).toResponseDTO(domainUser);
    }
}
