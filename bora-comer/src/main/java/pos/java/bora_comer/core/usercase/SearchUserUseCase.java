package pos.java.bora_comer.core.usercase;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;


public interface SearchUserUseCase {

    User findById(Long id) throws UserDomainException;

    Page<User> findAll(int page, int size) throws UserDomainException;
}
