package pos.java.bora_comer.core.usercase.user;

import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.UserDomainException;

public interface UpdateUserUseCase {

    User execute(User user) throws UserDomainException;

    void changeUserPassword(Long userId, String currentPassword, String newPassword) throws UserDomainException;


    User userAssociate(Long userId, Long tipoUsuarioId) throws UserDomainException;
}
