package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;
import pos.java.bora_comer.core.gateway.user.UserUpdateGateway;
import pos.java.bora_comer.core.usercase.user.UpdateUserUseCase;

import java.util.Optional;

@Service
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserUpdateGateway userUpdateGateway;
    private final UserSearchGateway userSearchGateway;

    public UpdateUserUseCaseImpl(UserUpdateGateway userUpdateGateway, UserSearchGateway userSearchGateway) {
        this.userUpdateGateway = userUpdateGateway;
        this.userSearchGateway = userSearchGateway;
    }

    @Override
    public User execute(User user) throws UserDomainException {
        try {
            return userUpdateGateway.update(user);
        } catch (IllegalArgumentException e) {
            throw new UserDomainException(e.getMessage());
        }
    }

    @Override
    public void changeUserPassword(Long userId, String currentPassword, String newPassword) throws UserDomainException {
        Optional<User> userOpt = userSearchGateway.findById(userId);

        User user = userOpt.orElseThrow(() -> new UserDomainException("Usuário não encontrado."));

        if (!user.getPassword().equals(currentPassword)) {
            throw new UserDomainException("Senha atual incorreta.");
        }

        user.updatePassword(newPassword);
        userUpdateGateway.update(user);
    }

    @Override
    public User userAssociate(Long userId, Long tipoUsuarioId) throws UserDomainException {

        return userUpdateGateway.associateUserType(userId, tipoUsuarioId);
    }

}
