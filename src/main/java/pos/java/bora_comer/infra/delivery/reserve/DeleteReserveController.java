package pos.java.bora_comer.infra.delivery.reserve;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.usercase.reserve.DeleteReserveUseCase;
import pos.java.bora_comer.infra.delivery.reserve.doc.DeleteReserveControllerDocs;

@RestController
@RequestMapping("/reserves")
public class DeleteReserveController implements DeleteReserveControllerDocs {

    private final DeleteReserveUseCase deleteReserveUseCase;

    public DeleteReserveController(DeleteReserveUseCase deleteReserveUseCase) {
        this.deleteReserveUseCase = deleteReserveUseCase;
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("Authorization") String authorization,
            @PathVariable Long id) {
        deleteReserveUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
