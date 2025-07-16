package pos.java.bora_comer.infra.delivery.restaurant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.usercase.restaurant.DeleteRestaurantUseCase;
import pos.java.bora_comer.infra.delivery.restaurant.doc.DeleteRestaurantControllerDocs;

@RestController
@RequestMapping("/restaurants")
public class DeleteRestaurantController implements DeleteRestaurantControllerDocs {

    private final DeleteRestaurantUseCase deleteRestaurantUseCase;

    public DeleteRestaurantController(DeleteRestaurantUseCase deleteRestaurantUseCase) {
        this.deleteRestaurantUseCase = deleteRestaurantUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteRestaurantUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
