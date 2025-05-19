package pos.java.bora_comer.infra.delivery.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressResponseDTO(
        @JsonProperty("rua") String street,
        @JsonProperty("bairro") String neighborhood,
        @JsonProperty("cidade") String city,
        @JsonProperty("estado") String state,
        @JsonProperty("cep") String zipCode
) {
}
