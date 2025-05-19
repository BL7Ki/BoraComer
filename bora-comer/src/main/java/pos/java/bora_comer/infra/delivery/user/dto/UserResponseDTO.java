package pos.java.bora_comer.infra.delivery.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponseDTO(
        @JsonProperty("id") Long id,
        @JsonProperty("nome") String name,
        @JsonProperty("email") String email,
        @JsonProperty("nome_usuario") String username,
        @JsonProperty("endereco") AddressResponseDTO address,
        @JsonProperty("tipo_usuario") String userRoleEnum,
        @JsonProperty("data_alteracao") String lastModifiedDate
) {
}
