package pos.java.bora_comer.infra.delivery.menu.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MenuItemResponseDTO(
        @JsonProperty("id") Long id, 
        @JsonProperty("nome") String name, 
        @JsonProperty("descricao") String description, 
        @JsonProperty("preco") BigDecimal price, 
        @JsonProperty("so_no_local") boolean inPlaceOnly, 
        @JsonProperty("imagem_caminho") String imagePath, 
        @JsonProperty("restaurante_id") Long restaurantId) {

}
