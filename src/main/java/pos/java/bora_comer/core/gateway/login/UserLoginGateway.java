package pos.java.bora_comer.core.gateway.login;

import pos.java.bora_comer.core.domain.user.User;

import java.util.Optional;

public interface UserLoginGateway {

    Optional<User> findByLogin(String login);
}
