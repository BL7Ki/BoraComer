package pos.java.bora_comer.core.gateway.user;


import pos.java.bora_comer.core.domain.User;

public interface UserGateway {

    boolean existsByUsername(String username);

    User save(User user);

}
