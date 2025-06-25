package pos.java.bora_comer.core.usercase.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserUpdateGateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UppdateUserUseCaseImplTest {

    private UserUpdateGateway userUpdateGateway;
    private UppdateUserUseCaseImpl uppdateUserUseCase;

    @BeforeEach
    void setUp() {
        userUpdateGateway = mock(UserUpdateGateway.class);
        uppdateUserUseCase = new UppdateUserUseCaseImpl(userUpdateGateway);
    }

    @Test
    void deveAtualizarUsuarioComSucesso() throws UserDomainException {
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

        when(userUpdateGateway.update(user)).thenReturn(user);

        User result = uppdateUserUseCase.execute(user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userUpdateGateway).update(user);
    }

    @Test
    void deveLancarUserDomainException_quandoUpdateGatewayLancarIllegalArgumentException() {
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

        when(userUpdateGateway.update(user))
                .thenThrow(new IllegalArgumentException("Dados inválidos"));

        UserDomainException exception = assertThrows(UserDomainException.class, () -> {
            uppdateUserUseCase.execute(user);
        });

        assertEquals("Dados inválidos", exception.getMessage());
        verify(userUpdateGateway).update(user);
    }
}
