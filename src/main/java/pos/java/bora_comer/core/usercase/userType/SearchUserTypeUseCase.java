package pos.java.bora_comer.core.usercase.userType;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;

public interface SearchUserTypeUseCase {

    Page<UserType> findAll(int page, int size) throws UserDomainException;

    UserType findById(Long id) throws UserDomainException;
}
