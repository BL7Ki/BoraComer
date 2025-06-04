package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserUpdateGateway;
import pos.java.bora_comer.core.usercase.user.UppdateUserUseCase;

@Service
public class UppdateUserUseCaseImpl implements UppdateUserUseCase {

    private final UserUpdateGateway userUpdateGateway;

    public UppdateUserUseCaseImpl(UserUpdateGateway userUpdateGateway) {
        this.userUpdateGateway = userUpdateGateway;
    }

    @Override
    public User execute(User user) throws UserDomainException {
        try {
            return userUpdateGateway.update(user);
        } catch (IllegalArgumentException e) {
            throw new UserDomainException(e.getMessage());
        }
    }
}
