package pos.java.bora_comer.infra.delivery.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserChangePasswordRequestDTO(

        @JsonProperty("username")
        @NotNull(message = "O nome de usuário não pode ser nulo.")
        @NotBlank(message = "O nome de usuário não pode ser vazio.")
        String username,

        @JsonProperty("senha_atual")
        @NotNull(message = "A senha atual não pode ser nula.")
        @NotBlank(message = "A senha atual não pode ser vazia.")
        String currentPassword,

        @JsonProperty("nova_senha")
        @NotNull(message = "A nova senha não pode ser nula.")
        @NotBlank(message = "A nova senha não pode ser vazia.")
        String newPassword
) {}