package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.util.factory.UserTestFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserSearchGatewayImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserSearchGatewayImpl userSearchGateway;

    private static final String TEST_USERNAME = "messi";

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userSearchGateway = new UserSearchGatewayImpl(userRepository, userMapper);
    }

    @Test
    void deveBuscarUsuarioPorUsernameQuandoExistir() {
        // Arrange
        UserEntity userEntity = UserTestFactory.umUserEntityPadrao();
        User user = UserTestFactory.umUserPadrao();

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity, userEntity.getUserTypeEntity())).thenReturn(user);

        // Act
        Optional<User> result = userSearchGateway.findByUsername(TEST_USERNAME);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(TEST_USERNAME, result.get().getUsername());
        verify(userRepository, times(1)).findByUsername(TEST_USERNAME);
        verify(userMapper, times(1)).toDomain(userEntity, userEntity.getUserTypeEntity());
    }

    @Test
    void deveRetornarOptionalVazioQuandoUsernameNaoExistir() {
        // Arrange
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userSearchGateway.findByUsername(TEST_USERNAME);

        // Assert
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByUsername(TEST_USERNAME);
        verifyNoInteractions(userMapper);
    }

    @Test
    void deveBuscarTodosUsuariosComPaginacao() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);

        UserEntity userEntity = UserTestFactory.umUserEntityPadrao();
        User user = UserTestFactory.umUserPadrao();

        Page<UserEntity> userEntityPage = new PageImpl<>(List.of(userEntity), pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(userEntityPage);
        when(userMapper.toDomain(userEntity, userEntity.getUserTypeEntity())).thenReturn(user);

        // Act
        Page<User> result = userSearchGateway.findAll(pageable);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals("Messi", result.getContent().getFirst().getName());

        verify(userRepository, times(1)).findAll(pageable);
        verify(userMapper, times(1)).toDomain(userEntity, userEntity.getUserTypeEntity());
    }
}