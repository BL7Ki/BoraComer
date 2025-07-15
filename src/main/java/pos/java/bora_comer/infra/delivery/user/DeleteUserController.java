package pos.java.bora_comer.infra.delivery.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pos.java.bora_comer.core.usercase.user.DeleteUserUseCase;
import pos.java.bora_comer.infra.delivery.user.doc.DeleteUserControllerDocs;

@RestController
@RequestMapping("/users")
public class DeleteUserController implements DeleteUserControllerDocs {

    private final DeleteUserUseCase deleteUserUseCase;

    public DeleteUserController(DeleteUserUseCase deleteUserUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {
        deleteUserUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}
