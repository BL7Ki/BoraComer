package pos.java.bora_comer.core.gateway.userType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pos.java.bora_comer.core.domain.UserType;

import java.util.Optional;

public interface UserTypeSearchGateway {

    Page<UserType> findAll(Pageable pageable);

    Optional<UserType> findById(Long id);
}
