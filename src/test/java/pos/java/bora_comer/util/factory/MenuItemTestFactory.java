package pos.java.bora_comer.util.factory;

import java.math.BigDecimal;

import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemRequestDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemResponseDTO;
import pos.java.bora_comer.infra.delivery.menu.dto.MenuItemUpdateRequestDTO;

public class MenuItemTestFactory {
    
    // Construtor privado para impedir instanciação
    private MenuItemTestFactory() {
        // impede instanciação
    }
    
    
    public static MenuItem createDefault() {
        return MenuItem.create(
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                1L
        );
    }

    public static MenuItem createDefaultWithId() {
        return MenuItem.create(
                10L,
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                1L
        );
    }

    public static MenuItemResponseDTO createResponseDTOWithId() {
        return new MenuItemResponseDTO(
                10L,
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                1L
        );
    }

    public static MenuItemRequestDTO createRequestDTOWithId() {
        return new MenuItemRequestDTO(
                "Sushi",
                "Sushi de salmão com arroz",
                BigDecimal.valueOf(29.99),
                true,
                "sushi.jpg",
                1L
        );
    }

    public static MenuItemUpdateRequestDTO createUpdateRequestDTOWithId() {
        return new MenuItemUpdateRequestDTO(
                "Updated Sushi",
                "Sushi de atum com arroz",
                BigDecimal.valueOf(34.99),
                false,
                "updated_sushi.jpg",
                2L
        );
    }

    public static MenuItem createCustom(Long id, String name, String description, BigDecimal price, boolean inPlaceOnly, String imagePath, Long restaurantId) {
        return MenuItem.create(
                id,
                name,
                description,
                price,
                inPlaceOnly,
                imagePath,
                restaurantId
        );
    }
}

