package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public UpdateUserUseCaseImpl(
            UserUpdateGateway userUpdateGateway,
            UserSearchGateway userSearchGateway,
            PasswordEncoder passwordEncoder
    ) {
        this.userUpdateGateway = userUpdateGateway;
        this.userSearchGateway = userSearchGateway;
        this.passwordEncoder = passwordEncoder;
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

        // Usa o PasswordEncoder para comparar o rawPassword com o hash
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new UserDomainException("Senha atual incorreta.");
        }

        // Codifica a nova senha antes de atualizar o objeto de Domínio
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedNewPassword);

        userUpdateGateway.update(user);
    }

    @Override
    public User userAssociate(Long userId, Long tipoUsuarioId) throws UserDomainException {
        return userUpdateGateway.associateUserType(userId, tipoUsuarioId);
    }

}