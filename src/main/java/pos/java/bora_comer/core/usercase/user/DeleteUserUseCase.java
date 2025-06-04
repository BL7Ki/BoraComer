package pos.java.bora_comer.core.usercase.user;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface DeleteUserUseCase {

    void execute(Long id) throws SummerNotFoundException;
}
