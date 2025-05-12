package pos.java.bora_comer.infra.delivery.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pos.java.bora_comer.core.usercase.DeleteUserUseCase;

@RestController
@RequestMapping("/users")
public class DeleteUserController {

    private final DeleteUserUseCase deleteUserUseCase;

    public DeleteUserController(DeleteUserUseCase deleteUserUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {
        deleteUserUseCase.execute(id);

        return ResponseEntity.noContent().build();
    }
}
