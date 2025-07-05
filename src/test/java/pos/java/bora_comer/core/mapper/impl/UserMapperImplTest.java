package pos.java.bora_comer.core.mapper.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.mapper.user.impl.UserMapperImpl;
import pos.java.bora_comer.infra.delivery.user.dto.*;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;
import pos.java.bora_comer.util.UserTestFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperImplTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapperImpl();
    }

    @Test
    void deveConverterUserRequestDTOParaDomain() {
        AddressRequestDTO addressDTO = new AddressRequestDTO("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        UserRequestDTO requestDTO = new UserRequestDTO("Messi", "messi@ex.com", "messi", "Messi@123", addressDTO, UserRoleRequestEnumDTO.CLIENTE);

        User user = userMapper.toDomain(requestDTO);

        assertEquals("Messi", user.getName());
        assertEquals("messi", user.getUsername());
        assertEquals("Messi@123", user.getPassword());
        assertEquals(UserRoleEnum.CLIENTE, user.getUserRoleEnum());
        assertNotNull(user.getAddress());
    }

    @Test
    void deveConverterDomainParaEntity() {
        Address address = Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        User user = User.create(1L, "Messi", "messi@ex.com", "messi", "Messi@123", address, UserRoleEnum.CLIENTE, "2024-06-25");

        UserEntity entity = userMapper.toEntity(user);

        assertEquals("Messi", entity.getName());
        assertEquals("messi@ex.com", entity.getEmail());
        assertEquals(UserRoleEntityEnum.CLIENTE, entity.getRole());
        assertNotNull(entity.getAddress());
    }

    @Test
    void deveConverterEntityParaDomain() {
        AddressEntity addressEntity = AddressEntity.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        UserEntity userEntity = UserEntity.create("Messi", "messi@ex.com", "messi", "Messi@123", addressEntity, UserRoleEntityEnum.ADMIN);

        // Definir campos privados via reflection
        ReflectionTestUtils.setField(userEntity, "id", 1L);
        ReflectionTestUtils.setField(userEntity, "lastModifiedDate", LocalDateTime.of(2024, 6, 25, 0, 0));

        User user = userMapper.toDomain(userEntity);

        assertEquals("Messi", user.getName());
        assertEquals("messi@ex.com", user.getEmail());
        assertEquals(UserRoleEnum.ADMIN, user.getUserRoleEnum());
        assertNotNull(user.getAddress());
    }

    @Test
    void deveConverterDomainParaResponseDTO() {
        Address address = Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        User user = UserTestFactory.umUserPadraoCliente();

        UserResponseDTO dto = userMapper.toResponseDTO(user);

        assertEquals("Messi", dto.name());
        assertEquals("messi@ex.com", dto.email());
        assertEquals("CLIENTE", dto.userRoleEnum());
    }

    @Test
    void deveConverterUserUpdateRequestDTOParaDomain() {
        AddressRequestDTO addressDTO = new AddressRequestDTO("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        UserUpdateRequestDTO updateDTO = new UserUpdateRequestDTO("Messi", "messi@ex.com", "NovaSenha@123", addressDTO);

        User user = userMapper.toDomain(updateDTO, 1L);

        assertEquals("Messi", user.getName());
        assertEquals("messi@ex.com", user.getEmail());
        assertEquals("NovaSenha@123", user.getPassword());
        assertEquals(1L, user.getId());
    }

    @Test
    void deveLancarExcecaoQuandoUserRoleRequestEnumForNulo() {
        AddressRequestDTO addressDTO = new AddressRequestDTO("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
        UserRequestDTO requestDTO = new UserRequestDTO("Messi", "messi@ex.com", "messi", "Messi@123", addressDTO, null);

        UserDomainException exception = assertThrows(UserDomainException.class, () -> {
            userMapper.toDomain(requestDTO);
        });

        assertEquals("UserRoleRequestEnumDTO não pode ser nulo", exception.getMessage());
    }
}
