package pos.java.bora_comer.infra.gateway.user.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.util.UserTestFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserSearchGatewayImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserSearchGatewayImpl userSearchGateway;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(UserMapper.class);
        userSearchGateway = new UserSearchGatewayImpl(userRepository, userMapper);
    }

    @Test
    void deveBuscarUsuarioPorIdQuandoExistir() {
        Long id = 1L;

        UserEntity userEntity = UserTestFactory.umUserEntityPadrao();
        User user = UserTestFactory.umUserPadrao();

        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> result = userSearchGateway.findById(id);

        assertTrue(result.isPresent());
        assertEquals("Messi", result.get().getName());
        verify(userRepository, times(1)).findById(id);
        verify(userMapper, times(1)).toDomain(userEntity);
    }

    @Test
    void deveRetornarOptionalVazioQuandoIdNaoExistir() {
        Long id = 2L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Optional<User> result = userSearchGateway.findById(id);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(id);
        verifyNoInteractions(userMapper);
    }

    @Test
    void deveBuscarTodosUsuariosComPaginacao() {
        Pageable pageable = PageRequest.of(0, 10);

        UserEntity userEntity = UserTestFactory.umUserEntityPadrao();
        User user = UserTestFactory.umUserPadrao();

        Page<UserEntity> userEntityPage = new PageImpl<>(List.of(userEntity), pageable, 1);

        when(userRepository.findAll(pageable)).thenReturn(userEntityPage);
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        Page<User> result = userSearchGateway.findAll(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Messi", result.getContent().get(0).getName());

        verify(userRepository, times(1)).findAll(pageable);
        verify(userMapper, times(1)).toDomain(userEntity);
    }
}
