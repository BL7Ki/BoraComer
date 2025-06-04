package pos.java.bora_comer.core.gateway.user;

import org.springframework.stereotype.Component;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

@Component
public class UserDeleteGatewayImpl implements UserDeleteGateway {

    private final UserRepository userRepository;

    public UserDeleteGatewayImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsById(Long id) throws SummerNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new SummerNotFoundException("User with ID " + id + " not found");
        }
        return true;
    }

    @Override
    public void deleteById(Long id) throws SummerNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new SummerNotFoundException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
