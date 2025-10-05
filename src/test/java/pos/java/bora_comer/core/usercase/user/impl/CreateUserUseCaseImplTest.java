package pos.java.bora_comer.core.usercase.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserCreateGateway;
import pos.java.bora_comer.util.factory.UserTestFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserUseCaseImplTest {

    private UserCreateGateway userCreateGateway;
    private PasswordEncoder passwordEncoder;
    private CreateUserUseCaseImpl createUserUseCase;

    @BeforeEach
    void setUp() {
        userCreateGateway = mock(UserCreateGateway.class);
        passwordEncoder = mock(PasswordEncoder.class);
        createUserUseCase = new CreateUserUseCaseImpl(userCreateGateway, passwordEncoder);
    }

    @Test
    void deveCriarUsuarioQuandoUsernameNaoExiste() throws UserDomainException {
        // ARRANGE
        String rawPassword = "senha123";
        String encodedPassword = "hashed_senha123";

        User user = UserTestFactory.umUserPadrao(); // Contém a senha RAW
        user.updatePassword(rawPassword);

        // Mock: Simula o encoder recebendo a senha RAW e retornando o HASH
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);

        when(userCreateGateway.existsByUsername(user.getUsername())).thenReturn(false);
        when(userCreateGateway.save(user)).thenReturn(user);

        // ACT
        User result = createUserUseCase.execute(user);

        // ASSERT
        assertNotNull(result);
        assertEquals(user, result);

        // Verifica se a senha do objeto de DOMÍNIO foi ATUALIZADA com o HASH
        assertEquals(encodedPassword, user.getPassword());

        verify(userCreateGateway).existsByUsername(user.getUsername());
        verify(passwordEncoder).encode(rawPassword);
        verify(userCreateGateway).save(user);
    }

    @Test
    void deveLancarExcecaoQuandoUsernameJaExiste() {
        // ARRANGE
        User user = UserTestFactory.umUserPadrao();

        when(userCreateGateway.existsByUsername(user.getUsername())).thenReturn(true);

        // ACT & ASSERT
        UserDomainException exception = assertThrows(UserDomainException.class, () -> createUserUseCase.execute(user));

        assertEquals("O userName já está em uso.", exception.getMessage());

        verify(userCreateGateway).existsByUsername(user.getUsername());
        verify(passwordEncoder, never()).encode(any());
        verify(userCreateGateway, never()).save(any());
    }
}