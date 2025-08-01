package pos.java.bora_comer.infra.gateway.menu.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import pos.java.bora_comer.core.domain.menu.MenuItem;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.mapper.menu.MenuItemMapper;
import pos.java.bora_comer.infra.persistence.repository.menu.MenuItemRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemSearchGatewayImplTest {

    private MenuItemRepository menuItemRepository;
    private MenuItemMapper menuItemMapper;
    private MenuItemSearchGatewayImpl gateway;

    @BeforeEach
    void setup() {
        menuItemRepository = mock(MenuItemRepository.class);
        menuItemMapper = mock(MenuItemMapper.class);
        gateway = new MenuItemSearchGatewayImpl(menuItemRepository, menuItemMapper);
    }

    @Test
    @DisplayName("Achar um item do menu por ID deve retornar o item quando encontrado")
    void findById_shouldReturnMenuItem_whenFound() {
        Long id = 1L;
        pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity entity = mock(pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity.class);
        when(entity.getId()).thenReturn(id);
        MenuItem domain = mock(MenuItem.class);

        when(menuItemRepository.findById(id)).thenReturn(Optional.of(entity));
        when(menuItemMapper.toDomain(entity)).thenReturn(domain);

        MenuItem result = gateway.findById(id);

        assertSame(domain, result);
        verify(menuItemRepository).findById(id);
        verify(menuItemMapper).toDomain(entity);
    }

    @Test
    @DisplayName("Achar um item do menu por ID deve lançar SummerNotFoundException quando não encontrado")
    void findById_shouldThrow_whenNotFound() {
        Long id = 1L;

        when(menuItemRepository.findById(id)).thenReturn(Optional.empty());

        SummerNotFoundException ex = assertThrows(SummerNotFoundException.class, () -> gateway.findById(id));
        assertEquals("Item do menu com ID " + id + " não encontrado.", ex.getMessage());

        verify(menuItemRepository).findById(id);
        verifyNoInteractions(menuItemMapper);
    }

    @Test
    @DisplayName("achar todos os itens do menu deve retornar uma lista de itens")
    void findAll_shouldReturnPagedRestaurants() {
        int page = 0;
        int size = 2;

        // Criando mocks das entidades reais
        pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity entity1 = mock(pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity.class);
        pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity entity2 = mock(pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity.class);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<pos.java.bora_comer.infra.persistence.repository.menu.entity.MenuItemEntity> entityPage = new PageImpl<>(List.of(entity1, entity2), pageRequest, 2);

        // Criando mocks dos domínios convertidos
        MenuItem domain1 = mock(MenuItem.class);
        MenuItem domain2 = mock(MenuItem.class);

        when(menuItemRepository.findAll(pageRequest)).thenReturn(entityPage);
        when(menuItemMapper.toDomain(entity1)).thenReturn(domain1);
        when(menuItemMapper.toDomain(entity2)).thenReturn(domain2);

        Page<MenuItem> resultPage = gateway.findAll(page, size);

        assertEquals(2, resultPage.getContent().size());
        assertTrue(resultPage.getContent().containsAll(List.of(domain1, domain2)));

        verify(menuItemRepository).findAll(pageRequest);
        verify(menuItemMapper).toDomain(entity1);
        verify(menuItemMapper).toDomain(entity2);
    }

}
