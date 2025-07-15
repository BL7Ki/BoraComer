package pos.java.bora_comer.core.usercase.userType;

import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;

public interface CreateUserTypeUseCase {

    UserType execute(UserType userType) throws UserDomainException;
}
