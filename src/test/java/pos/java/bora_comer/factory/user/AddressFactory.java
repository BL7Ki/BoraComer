package pos.java.bora_comer.factory.user;

import pos.java.bora_comer.infra.delivery.user.dto.AddressRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.AddressResponseDTO;

public class AddressFactory {

    public static AddressRequestDTO createAddressRequestDTO() {
        return new AddressRequestDTO(
                "Rua A",
                "Bairro B",
                "Cidade C",
                "SP",
                "12345-678"
        );
    }

    public static AddressResponseDTO createAddressResponseDTO() {
        return new AddressResponseDTO(
                "Rua A",
                "Bairro B",
                "Cidade C",
                "SP",
                "12345-678"
        );
    }
}
