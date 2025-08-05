package pos.java.bora_comer.infra.delivery.userType.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateUserTypeRequestDTO(

        @Schema(
                description = "Tipo de usuário. Valores permitidos: DONO_RESTAURANTE, CLIENTE. Opcional enviar",
                example = "CLIENTE",
                allowableValues = {"DONO_RESTAURANTE", "CLIENTE"}
        )
        @JsonProperty("name")
        @NotNull
        UserTypeNameRequestEnum name
) {
}
