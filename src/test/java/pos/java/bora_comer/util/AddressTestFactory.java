package pos.java.bora_comer.util;

import pos.java.bora_comer.core.domain.Address;
import pos.java.bora_comer.infra.persistence.repository.user.entity.AddressEntity;

public class AddressTestFactory {

    public static Address umEnderecoPadrao() {
        return Address.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
    }

    public static Address umEnderecoAtualizado() {
        return Address.create("Rua Nova", "Bairro Novo", "Cidade X", "SP", "98765-432");
    }

    public static AddressEntity umEnderecoEntityPadrao() {
        return AddressEntity.create("Rua A", "Bairro B", "Cidade C", "SP", "12345-678");
    }
}
