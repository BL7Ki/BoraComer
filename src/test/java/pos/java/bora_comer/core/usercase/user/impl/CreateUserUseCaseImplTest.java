package pos.java.bora_comer.core.usercase.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserCreateGateway;

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
    private CreateUserUseCaseImpl createUserUseCase;

    @BeforeEach
    void setUp() {
        userCreateGateway = mock(UserCreateGateway.class);
        createUserUseCase = new CreateUserUseCaseImpl(userCreateGateway);
    }

    @Test
    void deveCriarUsuarioQuandoUsernameNaoExiste() throws UserDomainException {
        // Mock do Address para simplificar
        Address someAddress = mock(Address.class);

        // Criação do usuário via método estático create
        User user = User.create(
                1L,
                "Lionel Messi",
                "messi@example.com",
                "messi",
                "Messi@123",
                someAddress,
                UserRoleEnum.CLIENTE,
                "2025-06-24"
        );

        when(userCreateGateway.existsByUsername(user.getUsername())).thenReturn(false);
        when(userCreateGateway.save(user)).thenReturn(user);

        User result = createUserUseCase.execute(user);

        assertNotNull(result);
        assertEquals(user, result);

        verify(userCreateGateway).existsByUsername(user.getUsername());
        verify(userCreateGateway).save(user);
    }

    @Test
    void deveLancarExcecaoQuandoUsernameJaExiste() {
        Address someAddress = mock(Address.class);

        User user = User.create(
                1L,
                "Lionel Messi",
                "messi@example.com",
                "messi",
                "Messi@123",
                someAddress,
                UserRoleEnum.CLIENTE,
                "2025-06-24"
        );

        when(userCreateGateway.existsByUsername(user.getUsername())).thenReturn(true);

        UserDomainException exception = assertThrows(UserDomainException.class, () -> {
            createUserUseCase.execute(user);
        });

        assertEquals("O userName já está em uso.", exception.getMessage());

        verify(userCreateGateway).existsByUsername(user.getUsername());
        verify(userCreateGateway, never()).save(any());
    }
}
