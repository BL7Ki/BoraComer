package pos.java.bora_comer.infra.delivery.menu.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MenuItemRequestDTO(
        @JsonProperty("nome") String name, 
        @JsonProperty("descricao") String description, 
        @JsonProperty("preco") BigDecimal price, 
        @JsonProperty("delivery") boolean delivery, 
        @JsonProperty("imagem_caminho") String imagePath, 
        @JsonProperty("restaurante_id") Long restaurantId
) {}
