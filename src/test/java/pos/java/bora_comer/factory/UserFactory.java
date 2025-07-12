package pos.java.bora_comer.factory;

import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
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
                UserRoleEnum.CLIENTE,
                "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700"
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
                UserRoleEnum.CLIENTE,
                null, null
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
                "2025-07-11T17:52:05.342190700"
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
                UserRoleEnum.CLIENTE,
                "2025-07-11T17:51:23.554623",
                "2025-07-11T17:52:05.342190700"
        );
    }

    public static UserEntity umUserEntityPadrao() {
        return UserEntity.create(
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                AddressEntity.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEntityEnum.CLIENTE
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
                UserRoleEntityEnum.valueOf(user.getUserRoleEnum().name())
        );
    }
}
