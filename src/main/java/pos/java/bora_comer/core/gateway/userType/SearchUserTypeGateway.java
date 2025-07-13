package pos.java.bora_comer.core.gateway.userType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pos.java.bora_comer.core.domain.UserType;

public interface SearchUserTypeGateway {

    Page<UserType> findAll(Pageable pageable);
}
