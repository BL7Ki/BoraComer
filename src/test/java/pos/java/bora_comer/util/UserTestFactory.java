package pos.java.bora_comer.util;

import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.domain.UserRoleEnum;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;

public class UserTestFactory {

    public static User umUserPadrao() {
        return User.create(
                1L,
                "Messi",
                "messi@ex.com",
                "messi",
                "Messi@123",
                Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678"),
                UserRoleEnum.CLIENTE,
                "2024-06-25"
        );
    }

    public static User umUserAtualizado() {
        return User.create(
                1L,
                "Messi Atualizado",
                "messi_novo@ex.com",
                "messi",
                "NovaSenha@123",
                Address.create("Rua Nova", "Bairro Novo", "Cidade X", "SP", "98765-432"),
                UserRoleEnum.ADMIN,
                "2024-06-26"
        );
    }

    public static User umUserNovoSemId() {
        return User.create(
                "Novo Messi",
                "novo@ex.com",
                "novomessi",
                "Messi@123",
                Address.create("Rua Nova", "Bairro Novo", "Cidade Z", "SP", "00000-000"),
                UserRoleEnum.CLIENTE,
                "2024-06-26"
        );
    }

    public static User umUserAdmin() {
        return User.create(
                2L,
                "Admin",
                "admin@ex.com",
                "admin",
                "Admin@123",
                Address.create("Rua Admin", "Centro", "Cidade Y", "RJ", "11111-111"),
                UserRoleEnum.ADMIN,
                "2024-06-26"
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
}
