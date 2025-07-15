package pos.java.bora_comer.infra.delivery.userType.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserTypeResponseDTO(

        @JsonProperty("id") Long id,
        @JsonProperty("tipo_usuario") String userType
) {
}
