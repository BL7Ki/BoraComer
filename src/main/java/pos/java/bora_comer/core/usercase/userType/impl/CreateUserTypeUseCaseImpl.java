package pos.java.bora_comer.core.usercase.userType.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.userType.UserTypeCreateGateway;
import pos.java.bora_comer.core.usercase.userType.CreateUserTypeUseCase;

@Service
public class CreateUserTypeUseCaseImpl implements CreateUserTypeUseCase {

    private final UserTypeCreateGateway userTypeCreateGateway;

    public CreateUserTypeUseCaseImpl(UserTypeCreateGateway userTypeCreateGateway) {
        this.userTypeCreateGateway = userTypeCreateGateway;
    }

    @Override
    public UserType execute(UserType userType) throws UserDomainException {
        return userTypeCreateGateway.save(userType);
    }
}
