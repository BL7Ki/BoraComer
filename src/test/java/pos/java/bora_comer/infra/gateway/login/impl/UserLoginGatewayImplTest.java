package pos.java.bora_comer.infra.gateway.login.impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.factory.UserFactory;

class UserLoginGatewayImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserLoginGatewayImpl userLoginGateway;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userLoginGateway = new UserLoginGatewayImpl(userRepository, userMapper);
    }

    @Test
    void deveRetornarUsuarioQuandoLoginExistir() {
        String login = "messi";

        var userEntity = UserFactory.umUserEntityPadrao();
        var user = UserFactory.umUserPadrao();

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        var result = userLoginGateway.findByLogin(login);

        assertTrue(result.isPresent());
        assertEquals("Messi", result.get().getName());
        verify(userRepository, times(1)).findByLogin(login);
        verify(userMapper, times(1)).toDomain(userEntity);
    }

    @Test
    void deveRetornarOptionalVazioQuandoLoginNaoExistir() {
        String login = "messi";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        var result = userLoginGateway.findByLogin(login);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByLogin(login);
        verifyNoInteractions(userMapper);
    }
}
