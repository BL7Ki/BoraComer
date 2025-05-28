package pos.java.bora_comer.core.usercase.user;

import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;

public interface CreateUserUseCase {

    User execute(User user) throws UserDomainException;
}
