package pos.java.bora_comer.infra.delivery.menu;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.usercase.menu.DeleteMenuItemUseCase;
import pos.java.bora_comer.infra.delivery.menu.doc.DeleteMenuItemControllerDocs;

@RestController
@RequestMapping("/menu-items")
public class DeleteMenuItemController implements DeleteMenuItemControllerDocs {

    private final DeleteMenuItemUseCase deleteMenuItemUseCase;

    public DeleteMenuItemController(DeleteMenuItemUseCase deleteMenuItemUseCase) {
        this.deleteMenuItemUseCase = deleteMenuItemUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        deleteMenuItemUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
