package pos.java.bora_comer.core.usercase.user;

import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;

public interface UppdateUserUseCase {

    /**
     * Método para atualizar um usuário existente.
     *
     * @param user Objeto que representa o usuário a ser atualizado.
     * @return UserDomain O usuário atualizado.
     * @throws UserDomainException Se ocorrer algum erro durante a atualização do usuário.
     */
    User execute(User user) throws UserDomainException;
}
