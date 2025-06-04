package pos.java.bora_comer.core.gateway.user;

import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;

public interface UserUpdateGateway {

    User update(User user) throws UserDomainException;
}
