package pos.java.bora_comer.factory.user;

import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.domain.UserTypeNameEnum;
import pos.java.bora_comer.infra.delivery.userType.dto.CreateUserTypeRequestDTO;
import pos.java.bora_comer.infra.delivery.userType.dto.UserTypeNameRequestEnum;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;

import java.util.Random;

public class UserTypeFactory {

    public static UserType createUserType() {
        return UserType.create(1l, UserTypeNameEnum.DONO_RESTAURANTE);
    }

    public static UserType createUserTypeIdRandomic() {

        Random random = new Random();
        Long id = random.nextLong(1, 1000);

        return UserType.create(id, UserTypeNameEnum.DONO_RESTAURANTE);
    }

    public static UserTypeEntity createUserTypeEntity() {

        return UserTypeEntity.create(UserTypeNameEntityEnum.DONO_RESTAURANTE);
    }

    public static UserTypeEntity createUserTypeEntityIdRandomic() {

        Random random = new Random();
        Long id = random.nextLong(1, 1000);

        return UserTypeEntity.create(id, UserTypeNameEntityEnum.DONO_RESTAURANTE);
    }

    public static CreateUserTypeRequestDTO createUserTypeRequestDTO() {
        return new CreateUserTypeRequestDTO(UserTypeNameRequestEnum.DONO_RESTAURANTE);
    }
}
