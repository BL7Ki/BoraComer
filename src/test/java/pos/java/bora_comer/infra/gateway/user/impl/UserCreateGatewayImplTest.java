package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.factory.UserFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCreateGatewayImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserCreateGatewayImpl userCreateGateway;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userCreateGateway = new UserCreateGatewayImpl(userRepository, userMapper);
    }

    @Test
    void deveVerificarSeUsernameExiste() {
        when(userRepository.existsByUsername("messi")).thenReturn(true);

        boolean exists = userCreateGateway.existsByUsername("messi");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("messi");
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        User user = UserFactory.umUserPadrao();
        UserEntity userEntity = UserFactory.umUserEntityPadrao();

        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        User result = userCreateGateway.save(user);

        assertNotNull(result);
        assertEquals("Messi", result.getName());
        verify(userMapper, times(1)).toEntity(user);
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toDomain(userEntity);
    }
}
