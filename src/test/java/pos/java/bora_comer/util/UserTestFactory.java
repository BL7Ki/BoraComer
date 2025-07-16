package pos.java.bora_comer.util;

import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.domain.user.UserRoleEnum;
import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;

public class UserTestFactory {

    private static final Long USER_TYPE_ID_DEFAULT = 1L;

    public static User umUserComId(Long id) {
        return User.create(
                id, "Messi", "messi@ex.com", "messi", "Messi@123",
                AddressTestFactory.umEnderecoPadrao(), UserRoleEnum.DEFAULT,
                "2024-06-25", "2024-06-25", UserTypeNameEnum.CLIENTE
        );
    }

    public static User umUserComIdRandomico() {
        long idRandomico = (long) (Math.random() * 10000);
        return umUserComId(idRandomico);
    }

    public static User umUserPadrao() {
        return umUserComId(1L);
    }

    public static User umUserAtualizado(Long id) {
        return User.create(
                id, "Messi Atualizado", "messi_novo@ex.com", "messi", "NovaSenha@123",
                AddressTestFactory.umEnderecoAtualizado(), UserRoleEnum.DEFAULT,
                "2024-06-26", "2024-06-26", UserTypeNameEnum.CLIENTE
        );
    }

    public static UserEntity umUserEntityPadrao() {
        return UserEntity.create(
                "Messi", "messi@ex.com", "messi", "Messi@123",
                AddressTestFactory.umEnderecoEntityPadrao(),
                UserRoleEntityEnum.DEFAULT, USER_TYPE_ID_DEFAULT
        );
    }

    public static UserEntity umUserEntityComDadosDe(User user) {
        return UserEntity.create(
                user.getName(), user.getEmail(), user.getUsername(), user.getPassword(),
                AddressEntity.create(
                        user.getAddress().getStreet(), user.getAddress().getCity(),
                        user.getAddress().getState(), user.getAddress().getZipCode(),
                        user.getAddress().getZipCode()
                ),
                UserRoleEntityEnum.valueOf(user.getUserRoleEnum().name()), USER_TYPE_ID_DEFAULT
        );
    }
}
