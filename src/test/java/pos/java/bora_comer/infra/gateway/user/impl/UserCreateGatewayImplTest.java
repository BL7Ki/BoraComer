package pos.java.bora_comer.infra.gateway.user.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;

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
        String username = "messi";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean exists = userCreateGateway.existsByUsername(username);

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        Address address = Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        User user = User.create("Messi", "messi@ex.com", "messi", "Messi@123", address, UserRoleEnum.CLIENTE, "2024-06-25");

        AddressEntity addressEntity = AddressEntity.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        UserEntity userEntity = UserEntity.create("Messi", "messi@ex.com", "messi", "Messi@123", addressEntity, UserRoleEntityEnum.CLIENTE);

        UserEntity savedUserEntity = UserEntity.create("Messi", "messi@ex.com", "messi", "Messi@123", addressEntity, UserRoleEntityEnum.CLIENTE);
        User savedUser = User.create("Messi", "messi@ex.com", "messi", "Messi@123", address, UserRoleEnum.CLIENTE, "2024-06-25");

        // Mocks
        when(userMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUserEntity);
        when(userMapper.toDomain(savedUserEntity)).thenReturn(savedUser);

        User result = userCreateGateway.save(user);

        assertNotNull(result);
        assertEquals("Messi", result.getName());
        assertEquals("messi@ex.com", result.getEmail());
        verify(userMapper, times(1)).toEntity(user);
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toDomain(savedUserEntity);
    }
}
