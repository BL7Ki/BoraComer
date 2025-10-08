package pos.java.bora_comer.core.gateway.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pos.java.bora_comer.core.domain.user.User;

import java.util.Optional;

public interface UserSearchGateway {

    Optional<User> findById(Long id);
    Page<User> findAll(Pageable pageable);
    Optional<User> findByUsername(String username);
}
