package pos.java.bora_comer.infra.delivery.user;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;
import pos.java.bora_comer.util.factory.UserTestFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchUserControllerTest {

    @Mock
    private SearchUserUseCase searchUserUseCase;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private SearchUserController controller;

    private static final String TEST_USERNAME = "messi";

    @Test
    void deveriaBuscarUsuarioPorUsernameERetornar200() {
        // Arrange
        User domainUser = UserTestFactory.umUserPadrao();
        UserResponseDTO responseDTO = UserTestFactory.createUserResponseDTO();

        when(searchUserUseCase.findByUsername(TEST_USERNAME)).thenReturn(domainUser);
        when(userMapper.toResponseDTO(domainUser)).thenReturn(responseDTO);

        // Act
        ResponseEntity<UserResponseDTO> response = controller.findByUsername(TEST_USERNAME);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDTO, response.getBody());
        verify(searchUserUseCase).findByUsername(TEST_USERNAME);
        verify(userMapper).toResponseDTO(domainUser);
    }

    @Test
    void deveriaBuscarTodosUsuariosComPaginacaoERetornar200() {
        // Arrange
        int page = 0;
        int size = 2;

        User user1 = UserTestFactory.umUserComIdRandomico();
        User user2 = UserTestFactory.umUserComIdRandomico();

        List<User> userList = List.of(user1, user2);
        Page<User> userPage = new PageImpl<>(userList);

        UserResponseDTO responseDTO1 = UserTestFactory.createUserResponseDTOIdRandomico();
        UserResponseDTO responseDTO2 = UserTestFactory.createUserResponseDTOIdRandomico();

        when(searchUserUseCase.findAll(page, size)).thenReturn(userPage);
        when(userMapper.toResponseDTO(user1)).thenReturn(responseDTO1);
        when(userMapper.toResponseDTO(user2)).thenReturn(responseDTO2);

        // Act
        ResponseEntity<List<UserResponseDTO>> response = controller.findAll(page, size);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<UserResponseDTO> body = response.getBody();
        assertNotNull(body);
        assertEquals(2, body.size());
        assertTrue(body.contains(responseDTO1));
        assertTrue(body.contains(responseDTO2));

        verify(searchUserUseCase).findAll(page, size);
        verify(userMapper).toResponseDTO(user1);
        verify(userMapper).toResponseDTO(user2);
    }
}