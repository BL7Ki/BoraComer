package pos.java.bora_comer.core.usercase.user;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.errors.UserDomainException;


public interface SearchUserUseCase {

    User findByUsername(String Username) throws SummerNotFoundException;

    Page<User> findAll(int page, int size) throws UserDomainException;
}
