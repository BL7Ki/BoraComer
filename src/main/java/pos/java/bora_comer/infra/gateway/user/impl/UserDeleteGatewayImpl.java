package pos.java.bora_comer.infra.gateway.user.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.user.UserDeleteGateway;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

@Component
public class UserDeleteGatewayImpl implements UserDeleteGateway {

    private final UserRepository userRepository;

    public UserDeleteGatewayImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new SummerNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
