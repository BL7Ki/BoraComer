package pos.java.bora_comer.core.usercase.menu;

import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface DeleteMenuItemUseCase {

    void execute(Long id) throws SummerNotFoundException;
}
