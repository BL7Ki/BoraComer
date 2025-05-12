package pos.java.bora_comer.infra.delivery.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserUpdateRequestDTO(

        @JsonProperty("nome") String name,
        @JsonProperty("email") String email,
        @JsonProperty("senha") String password,
        @JsonProperty("endereco") AddressRequestDTO address
) {
}
