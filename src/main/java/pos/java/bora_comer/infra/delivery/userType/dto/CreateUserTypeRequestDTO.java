package pos.java.bora_comer.infra.delivery.userType.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record CreateUserTypeRequestDTO(
        @JsonProperty("name")
        @NotNull
        UserTypeNameRequestEnum name
) {
}
