package pos.java.bora_comer.infra.gateway.login.impl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;

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

        // Mocks
        AddressEntity addressEntity = AddressEntity.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        UserEntity userEntity = UserEntity.create("Messi", "messi@ex.com", "messi", "Messi@123", addressEntity, pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum.CLIENTE);
        User user = User.create("Messi", "messi@ex.com", "messi", "Messi@123",
                Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEnum.CLIENTE, "2024-06-25");

        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity)).thenReturn(user);

        Optional<User> result = userLoginGateway.findByLogin(login);

        assertTrue(result.isPresent());
        assertEquals("Messi", result.get().getName());
        verify(userRepository, times(1)).findByLogin(login);
        verify(userMapper, times(1)).toDomain(userEntity);
    }

    @Test
    void deveRetornarOptionalVazioQuandoLoginNaoExistir() {
        String login = "messi";

        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        Optional<User> result = userLoginGateway.findByLogin(login);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findByLogin(login);
        verifyNoInteractions(userMapper);
    }
}
