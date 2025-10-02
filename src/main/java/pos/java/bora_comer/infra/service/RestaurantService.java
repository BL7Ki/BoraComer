package pos.java.bora_comer.infra.service;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.restaurant.Restaurant;
import pos.java.bora_comer.core.mapper.restaurant.RestaurantMapper;
import pos.java.bora_comer.infra.persistence.repository.restaurant.RestaurantRepository;
import pos.java.bora_comer.infra.persistence.repository.restaurant.entity.RestaurantEntity;
// import pos.java.bora_comer.infra.security.auth.SecurityContextUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    // private final SecurityContextUtils securityContextUtils;

    public RestaurantService(RestaurantRepository restaurantRepository,
                             RestaurantMapper restaurantMapper /*, SecurityContextUtils securityContextUtils*/) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        // this.securityContextUtils = securityContextUtils;
    }

    // --- QUERY RESOLVERS (Leitura) ---

    public Restaurant findById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with ID: " + id));
    }

    public List<Restaurant> findAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toDomain)
                .collect(Collectors.toList());
    }

    // --- MUTATION RESOLVERS (Escrita) ---

    public Restaurant create(String name, String address, String cuisineType, String openingHours) {
        if (restaurantRepository.existsByName(name)) {
            throw new IllegalArgumentException("Restaurant already exists with name: " + name);
        }

        // Long currentOwnerId = securityContextUtils.getCurrentUserId();
        Long currentOwnerId = 99L; // MOCK temporário para OwnerID

        // 1. Cria o objeto de domínio com dados REAIS fornecidos pelo GraphQL
        Restaurant newRestaurantDomain = Restaurant.create(
                name,
                address,
                cuisineType,
                openingHours,
                currentOwnerId
        );

        // 2. Converte para a entidade JPA e salva
        RestaurantEntity entityToSave = restaurantMapper.toEntity(newRestaurantDomain);
        RestaurantEntity savedEntity = restaurantRepository.save(entityToSave);

        // 3. Retorna o domínio mapeado com o ID
        return restaurantMapper.toDomain(savedEntity);
    }

    public Restaurant update(Long id, String name, String address, String cuisineType, String openingHours) {
        RestaurantEntity existingEntity = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found with ID: " + id));

        boolean updated = false;
        if (name != null) {
            existingEntity.updateName(name);
            updated = true;
        }
        if (address != null) {
            existingEntity.updateAddress(address);
            updated = true;
        }

        if (cuisineType != null) {
            existingEntity.updateCuisineType(cuisineType);
            updated = true;
        }

        if (openingHours != null) {
            existingEntity.updateOpeningHours(openingHours);
            updated = true;
        }

        if (updated) {
            existingEntity.updateLastModifiedDate();
            RestaurantEntity savedEntity = restaurantRepository.save(existingEntity);
            return restaurantMapper.toDomain(savedEntity);
        }

        return restaurantMapper.toDomain(existingEntity);
    }

    public void delete(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete: Restaurant not found with ID: " + id);
        }
        restaurantRepository.deleteById(id);
    }
}