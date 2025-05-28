package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.user.UserMapper;
import pos.java.bora_comer.core.usercase.user.DeleteUserUseCase;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;

@Service
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserRepository userRepository;

    public DeleteUserUseCaseImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Long id) throws UserDomainException {
        if (!userRepository.existsById(id)) {
            throw new UserDomainException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }
}
