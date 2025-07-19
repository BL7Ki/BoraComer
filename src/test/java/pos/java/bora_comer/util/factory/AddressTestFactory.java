package pos.java.bora_comer.util.factory;

import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.infra.delivery.user.dto.AddressRequestDTO;
import pos.java.bora_comer.infra.delivery.user.dto.AddressResponseDTO;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;

public class AddressTestFactory {

    // Endereço padrão
    private static final String STREET_DEFAULT = "Rua A";
    private static final String DISTRICT_DEFAULT = "Bairro B";
    private static final String CITY_DEFAULT = "Cidade C";
    private static final String STATE_DEFAULT = "SP";
    private static final String ZIP_CODE_DEFAULT = "12345-678";

    // Endereço atualizado
    private static final String STREET_UPDATED = "Rua Nova";
    private static final String DISTRICT_UPDATED = "Bairro Novo";
    private static final String CITY_UPDATED = "Cidade X";
    private static final String ZIP_CODE_UPDATED = "98765-432";

    public static Address umEnderecoPadrao() {
        return Address.create(STREET_DEFAULT, DISTRICT_DEFAULT, CITY_DEFAULT, STATE_DEFAULT, ZIP_CODE_DEFAULT);
    }

    public static Address umEnderecoAtualizado() {
        return Address.create(STREET_UPDATED, DISTRICT_UPDATED, CITY_UPDATED, STATE_DEFAULT, ZIP_CODE_UPDATED);
    }

    public static AddressEntity umEnderecoEntityPadrao() {
        return AddressEntity.create(STREET_DEFAULT, DISTRICT_DEFAULT, CITY_DEFAULT, STATE_DEFAULT, ZIP_CODE_DEFAULT);
    }

    public static AddressRequestDTO createAddressRequestDTO() {
        return new AddressRequestDTO(STREET_DEFAULT, DISTRICT_DEFAULT, CITY_DEFAULT, STATE_DEFAULT, ZIP_CODE_DEFAULT);
    }

    public static AddressResponseDTO createAddressResponseDTO() {
        return new AddressResponseDTO(STREET_DEFAULT, DISTRICT_DEFAULT, CITY_DEFAULT, STATE_DEFAULT, ZIP_CODE_DEFAULT);
    }
}
