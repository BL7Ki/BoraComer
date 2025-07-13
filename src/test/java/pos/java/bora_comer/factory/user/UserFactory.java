package pos.java.bora_comer.factory.user;

import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.core.domain.UserTypeNameEnum;
import pos.java.bora_comer.infra.delivery.user.dto.*;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;

public class UserFactory {

    public static User umUserComId(Long id) {
        return User.create(
                id,
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEnum.DEFAULT,
                "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700", UserTypeNameEnum.DONO_RESTAURANTE
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
                null, null, UserTypeNameEnum.DONO_RESTAURANTE
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
                "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700", UserTypeNameEnum.DONO_RESTAURANTE
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
                "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700", UserTypeNameEnum.DONO_RESTAURANTE
        );
    }

    public static UserEntity umUserEntityPadrao() {
        return UserEntity.create(
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                AddressEntity.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEntityEnum.DEFAULT, 1l
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
                UserRoleEntityEnum.valueOf(user.getUserRoleEnum().name()), 1l
        );
    }

    public static UserRequestDTO createUserRequestDTO() {

        AddressRequestDTO addressRequest = AdressFactory.createAddressRequestDTO();
        CreateUserTypeRequestDTO createUserTypeRequestDTO = new CreateUserTypeRequestDTO(UserTypeNameRequestEnum.DONO_RESTAURANTE);
        UserRoleRequestEnumDTO userType = UserRoleRequestEnumDTO.DEFAULT;
        // Cria e retorna um UserRequestDTO com os dados necessários
        return new UserRequestDTO(
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                "senha123",
                addressRequest,
                userType, createUserTypeRequestDTO
        );
    }

    public static UserRequestDTO createUserRoleNullRequestDTO() {

        AddressRequestDTO addressRequest = AdressFactory.createAddressRequestDTO();
        CreateUserTypeRequestDTO createUserTypeRequestDTO = new CreateUserTypeRequestDTO(UserTypeNameRequestEnum.DONO_RESTAURANTE);

        return new UserRequestDTO("Messi", "messi@ex.com", "messi", "Messi@123", addressRequest, null, createUserTypeRequestDTO);
    }

    public static UserResponseDTO createUserResponseDTOIdRandomico() {
        long idRandomico = (long) (Math.random() * 10000);
        AddressResponseDTO addressResponse = AdressFactory.createAddressResponseDTO();
        return new UserResponseDTO(
                idRandomico,
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                addressResponse,
                "CLIENTE",
                "2025-07-11T17:51:23.554623",
                null,
                UserTypeNameEnum.DONO_RESTAURANTE.name()
        );
    }

    public static UserResponseDTO createUserResponseDTO() {

        AddressResponseDTO addressResponse = AdressFactory.createAddressResponseDTO();

        // Cria e retorna um UserResponseDTO com os dados necessários
        return new UserResponseDTO(
                1L,
                "Leo Messi",
                "leomessi@email.com",
                "messi10",
                addressResponse,
                "CLIENTE",
                "2025-07-11T17:51:23.554623",
                null, UserTypeNameEnum.DONO_RESTAURANTE.name()
        );
    }

    public static UserUpdateRequestDTO createUserUpdateRequestDTO() {
        AddressRequestDTO addressRequest = AdressFactory.createAddressRequestDTO();

        return new UserUpdateRequestDTO(
                "Messi",
                "messi@ex.com",
                "NovaSenha@123",
                addressRequest, UserTypeNameRequestEnum.DONO_RESTAURANTE);

    }
}
