package pos.java.bora_comer.infra.persistence.repository.userType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeNameEntityEnum;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {

    boolean existsByName(UserTypeNameEntityEnum name);
}
