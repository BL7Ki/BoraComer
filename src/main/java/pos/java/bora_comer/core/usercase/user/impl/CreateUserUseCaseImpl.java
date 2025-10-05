package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.security.crypto.password.PasswordEncoder; // Novo import
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserCreateGateway;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserCreateGateway userCreateGateway;
    private final PasswordEncoder passwordEncoder; // Injeção necessária

    public CreateUserUseCaseImpl(UserCreateGateway userCreateGateway, PasswordEncoder passwordEncoder) {
        this.userCreateGateway = userCreateGateway;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User execute(User user) throws UserDomainException {

        if (userCreateGateway.existsByUsername(user.getUsername())) {
            throw new UserDomainException("O userName já está em uso.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.updatePassword(encodedPassword);

        return userCreateGateway.save(user);
    }
}