package pos.java.bora_comer.core.gateway.user;


import pos.java.bora_comer.core.domain.user.User;

public interface UserCreateGateway {

    boolean existsByUsername(String username);

    User save(User user);

}
