package pos.java.bora_comer.infra.delivery.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserChangePasswordRequestDTO(

        @JsonProperty("senha_atual")
        String currentPassword,

        @JsonProperty("nova_senha")
        @NotNull(message = "A nova senha não pode ser nula.")
        @NotBlank(message = "A nova senha não pode ser vazia.")
        String newPassword
) {}
