package pos.java.bora_comer.util.factory;

import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.domain.user.UserRoleEnum;
import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import pos.java.bora_comer.infra.delivery.user.dto.*;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;

public class UserTestFactory {

    private static final Long USER_TYPE_ID_DEFAULT = 1L;
    private static final String DATA_CRIACAO = "2025-07-11T17:51:23.554623";
    private static final String DATA_ATUALIZACAO = "2025-07-11T17:52:05.342190700";

    public static User umUserComId(Long id) {
        return User.create(
                id,
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEnum.DEFAULT,
                DATA_CRIACAO,
                DATA_ATUALIZACAO,
                UserTypeNameEnum.DONO_RESTAURANTE
        );
    }

    public static User umUserComIdRandomico() {
        long idRandomico = (long) (Math.random() * 10000);
        return umUserComId(idRandomico);
    }

    public static User umUserPadrao() {
        return User.create(
                null,
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEnum.DEFAULT,
                null,
                null,
                UserTypeNameEnum.DONO_RESTAURANTE
        );
    }

    public static User umUserTypeNull() {
        return User.create(
                null,
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEnum.DEFAULT,
                null,
                null,
                null
        );
    }

    public static User umUserAtualizado(Long id) {
        return User.create(
                id,
                "Messi Atualizado",
                "messi_novo@ex.com",
                "messi",
                "NovaSenha@123",
                Address.create("Rua Nova", "Bairro Novo", "Cidade X", "SP", "98765-432"),
                UserRoleEnum.ADMIN,
                DATA_CRIACAO,
                DATA_ATUALIZACAO,
                UserTypeNameEnum.DONO_RESTAURANTE
        );
    }

    public static User umUserPadraoCliente() {
        return User.create(
                1L,
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEnum.DEFAULT,
                DATA_CRIACAO,
                DATA_ATUALIZACAO,
                UserTypeNameEnum.DONO_RESTAURANTE
        );
    }

    public static UserEntity umUserEntityPadrao() {
        return UserEntity.create(
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                AddressEntity.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEntityEnum.DEFAULT,
                USER_TYPE_ID_DEFAULT
        );
    }

    public static UserEntity umUserEntityComDadosDe(User user) {
        return UserEntity.create(
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                AddressEntity.create(
                        user.getAddress().getStreet(),
                        user.getAddress().getCity(),
                        user.getAddress().getState(),
                        user.getAddress().getZipCode(),
                        user.getAddress().getZipCode()
                ),
                UserRoleEntityEnum.valueOf(user.getUserRoleEnum().name()),
                USER_TYPE_ID_DEFAULT
        );
    }

    public static UserRequestDTO createUserRequestDTO() {
        return new UserRequestDTO(
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                "senha123",
                AddressTestFactory.createAddressRequestDTO(),
                UserRoleRequestEnumDTO.DEFAULT,
                new CreateUserTypeRequestDTO(UserTypeNameRequestEnum.DONO_RESTAURANTE)
        );
    }

    public static UserRequestDTO createUserRoleNullRequestDTO() {
        return new UserRequestDTO(
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                AddressTestFactory.createAddressRequestDTO(),
                null,
                new CreateUserTypeRequestDTO(UserTypeNameRequestEnum.DONO_RESTAURANTE)
        );
    }

    public static UserResponseDTO createUserResponseDTOIdRandomico() {
        long idRandomico = (long) (Math.random() * 10000);
        return new UserResponseDTO(
                idRandomico,
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                AddressTestFactory.createAddressResponseDTO(),
                "CLIENTE",
                DATA_CRIACAO,
                null,
                UserTypeNameEnum.DONO_RESTAURANTE.name()
        );
    }

    public static UserResponseDTO createUserResponseDTO() {
        return new UserResponseDTO(
                1L,
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                AddressTestFactory.createAddressResponseDTO(),
                "CLIENTE",
                DATA_CRIACAO,
                null,
                UserTypeNameEnum.DONO_RESTAURANTE.name()
        );
    }

    public static UserUpdateRequestDTO createUserUpdateRequestDTO() {
        return new UserUpdateRequestDTO(
                "Messi",
                "messi@ex.com",
                "NovaSenha@123",
                AddressTestFactory.createAddressRequestDTO(),
                UserTypeNameRequestEnum.DONO_RESTAURANTE
        );
    }
}
