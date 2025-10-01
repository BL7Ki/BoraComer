package pos.java.bora_comer.infra.gateway.login.impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.util.factory.UserTestFactory;

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

        var userEntity = UserTestFactory.umUserEntityPadrao();
        var user = UserTestFactory.umUserPadrao();

        when(userRepository.findByUsername(login)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity, userEntity.getUserTypeEntity())).thenReturn(user);

        var result = userLoginGateway.findByLogin(login);

        assertTrue(result.isPresent());
        assertEquals("Messi", result.get().getName());
        verify(userRepository, times(1)).findByUsername(login);
        verify(userMapper, times(1)).toDomain(userEntity, userEntity.getUserTypeEntity());
    }

    @Test
    void deveRetornarOptionalVazioQuandoLoginNaoExistir() {
        String login = "messi";

        when(userRepository.findByUsername(login)).thenReturn(Optional.empty());

        var result = userLoginGateway.findByLogin(login);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByUsername(login);
        verifyNoInteractions(userMapper);
    }
}
