package pos.java.bora_comer.core.usercase.user;

import pos.java.bora_comer.core.errors.UserDomainException;

public interface DeleteUserUseCase {

    void execute(Long id) throws UserDomainException;
}
