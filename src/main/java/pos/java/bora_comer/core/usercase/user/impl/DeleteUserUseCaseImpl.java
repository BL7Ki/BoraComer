package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.user.UserDeleteGateway;
import pos.java.bora_comer.core.usercase.user.DeleteUserUseCase;

@Service
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserDeleteGateway userDeleteGateway;

    public DeleteUserUseCaseImpl(UserDeleteGateway userDeleteGateway) {
        this.userDeleteGateway = userDeleteGateway;
    }

    @Override
    public void execute(Long id) throws SummerNotFoundException {
        userDeleteGateway.deleteById(id);
    }
}
