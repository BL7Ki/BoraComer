package pos.java.bora_comer.infra.delivery.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.usercase.user.DeleteUserUseCase;
import pos.java.bora_comer.infra.delivery.user.doc.DeleteUserControllerDocs;

@RestController
@RequestMapping("/users")
public class DeleteUserController implements DeleteUserControllerDocs {

    private final DeleteUserUseCase deleteUserUseCase;

    public DeleteUserController(DeleteUserUseCase deleteUserUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        deleteUserUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}
