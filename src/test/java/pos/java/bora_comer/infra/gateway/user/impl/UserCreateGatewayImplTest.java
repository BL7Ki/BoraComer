package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.factory.user.UserFactory;
import pos.java.bora_comer.infra.persistence.repository.userType.UserTypeRepository;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCreateGatewayImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserCreateGatewayImpl userCreateGateway;
    private UserTypeRepository userTypeRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userTypeRepository = mock(UserTypeRepository.class);
        userCreateGateway = new UserCreateGatewayImpl(userRepository, userMapper, userTypeRepository);
    }

    @Test
    void deveVerificarSeUsernameExiste() {
        when(userRepository.existsByUsername("messi")).thenReturn(true);
        when(userTypeRepository.findByName(any()))
                .thenReturn(Optional.of(UserTypeEntity.create(UserTypeNameEntityEnum.DONO_RESTAURANTE)));

        boolean exists = userCreateGateway.existsByUsername("messi");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("messi");
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        User user = UserFactory.umUserPadrao();
        UserEntity userEntity = UserFactory.umUserEntityPadrao();

        UserTypeEntity userTypeEntity = UserTypeEntity.create(1l, UserTypeNameEntityEnum.DONO_RESTAURANTE);


        when(userTypeRepository.findByName(any()))
                .thenReturn(Optional.of(userTypeEntity));

        when(userMapper.toEntity(any(), anyLong())).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDomain(userEntity, userEntity.getUserTypeEntity())).thenReturn(user);

        User result = userCreateGateway.save(user);

        assertNotNull(result);
        assertEquals("Messi", result.getName());
        verify(userMapper, times(1)).toEntity(user, 1l);
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toDomain(userEntity, userEntity.getUserTypeEntity());
    }
}
