package pos.java.bora_comer.core.gateway.userType;

import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;

public interface UserTypeCreateGateway {

    UserType save(UserType userType) throws UserDomainException;
}
