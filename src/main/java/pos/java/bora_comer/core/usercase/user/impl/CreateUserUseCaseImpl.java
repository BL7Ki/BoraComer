package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserGateway;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;

@Service
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserGateway userGateway;

    public CreateUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }
    /**
     * Método para criar um novo usuário.
     *
     * @param user Objeto que representa o usuário a ser criado.
     * @return UserDomain O usuário criado.
     * @throws UserDomainException Se ocorrer algum erro durante a criação do usuário.
     */

    @Override
    public User execute(User user) throws UserDomainException {

        if (userGateway.existsByUsername(user.getUsername())) {
            throw new UserDomainException("O userName já está em uso.");
        }

        return userGateway.save(user);
    }
}
