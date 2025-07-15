package pos.java.bora_comer.core.usercase.userType.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.userType.UserTypeCreateGateway;
import pos.java.bora_comer.factory.user.UserTypeFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserTypeUseCaseImplTest {

    private UserTypeCreateGateway userTypeCreateGateway;
    private CreateUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userTypeCreateGateway = mock(UserTypeCreateGateway.class);
        useCase = new CreateUserTypeUseCaseImpl(userTypeCreateGateway);
    }

    @Test
    void deveCriarUserTypeComSucesso() throws UserDomainException {
        UserType userType = UserTypeFactory.createUserType();
        UserType userTypeSalvo = UserTypeFactory.createUserType();

        when(userTypeCreateGateway.save(userType)).thenReturn(userTypeSalvo);

        UserType resultado = useCase.execute(userType);

        assertEquals(userTypeSalvo, resultado);
        verify(userTypeCreateGateway).save(userType);
    }

    @Test
    void devePropagarExcecaoDoGateway() throws UserDomainException {
        UserType userType = UserTypeFactory.createUserType();
        when(userTypeCreateGateway.save(userType)).thenThrow(new UserDomainException("erro"));

        assertThrows(UserDomainException.class, () -> useCase.execute(userType));
        verify(userTypeCreateGateway).save(userType);
    }

}