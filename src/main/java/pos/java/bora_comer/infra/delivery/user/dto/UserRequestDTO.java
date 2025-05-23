package pos.java.bora_comer.infra.delivery.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequestDTO(
        @JsonProperty("nome") String name,
        @JsonProperty("email") String email,
        @JsonProperty("login") String username,
        @JsonProperty("senha") String password,
        @JsonProperty("endereco") AddressRequestDTO addressRequestDTO,
        @JsonProperty("tipo_usuario") UserRoleRequestEnumDTO userType

) {
}
