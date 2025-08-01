package pos.java.bora_comer.core.usercase.menu;

import org.springframework.data.domain.Page;
import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.MenuItemDomainException;
import pos.java.bora_comer.core.errors.SummerNotFoundException;

public interface SearchMenuItemUseCase {

    MenuItem findById(Long id) throws SummerNotFoundException;

    Page<MenuItem> findAll(int page, int size) throws MenuItemDomainException;
}
