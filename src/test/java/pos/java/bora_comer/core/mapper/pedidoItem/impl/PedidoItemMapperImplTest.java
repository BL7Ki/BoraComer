package pos.java.bora_comer.core.mapper.pedidoItem.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pos.java.bora_comer.core.domain.pedidoItem.PedidoItem;
import pos.java.bora_comer.core.errors.PedidoItemDomainException;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemRequestDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemUpdateRequestDTO;
import pos.java.bora_comer.infra.persistence.repository.pedidoItem.entity.PedidoItemEntity;
import static org.junit.jupiter.api.Assertions.*;
import static pos.java.bora_comer.util.factory.PedidoItemTestFactory.createDefault;

import java.time.LocalDateTime;

class PedidoItemMapperImplTest {

    private PedidoItemMapperImpl mapper;
    

    @BeforeEach
    void setUp() {
        mapper = new PedidoItemMapperImpl();
    }

    @Test
    @DisplayName("toDomain(PedidoRequestDTO) returns valid domain")
    void toDomain_FromRequestDTO_ShouldMapCorrectly() {
        var requestDTO = new PedidoItemRequestDTO(
                1L,
                1L,
                2,
                LocalDateTime.parse("2024-10-10T12:00:00")
        );

        PedidoItem domain = mapper.toDomain(requestDTO);

        assertNotNull(domain);
        assertNull(domain.getId());
        assertEquals(1L, domain.getPedidoId());
        assertEquals(1L, domain.getMenuItemId());
        assertEquals(2, domain.getQuantity());
    }

    @Test
    @DisplayName("toDomain(PedidoItemRequestDTO) throws exception on null")
    void toDomain_FromRequestDTO_ShouldThrowOnNull() {
        assertThrows(PedidoItemDomainException.class, () -> mapper.toDomain((PedidoItemRequestDTO) null));
    }

    @Test
    @DisplayName("toEntity(PedidoItem) returns valid entity")
    void toEntity_FromDomain_ShouldMapCorrectly() {
        PedidoItem domain = createDefault();

        var entity = mapper.toEntity(domain);

        assertNotNull(entity);
        assertNull(entity.getId());
        assertEquals(1L, entity.getPedidoId());
        assertEquals(1L, entity.getMenuItemId());
        assertEquals(2, entity.getQuantity());
    }

    @Test
    @DisplayName("toEntity(PedidoItem) throws exception on null")
    void toEntity_FromDomain_ShouldThrowOnNull() {
        assertThrows(PedidoItemDomainException.class, () -> mapper.toEntity(null));
    }

    @Test
    @DisplayName("toDomain(PedidoItemEntity) returns valid domain with ID")
    void toDomain_FromEntity_ShouldMapCorrectly() {
        var entity = PedidoItemEntity.create(
                1L,
                1L,
                2   
        );

        var domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getPedidoId());
        assertEquals(1L, domain.getMenuItemId());
        assertEquals(2, domain.getQuantity());  
    }

    @Test
    @DisplayName("toDomain(PedidoItemEntity) throws exception on null")
    void toDomain_FromEntity_ShouldThrowOnNull() {
        assertThrows(PedidoItemDomainException.class, () -> mapper.toDomain((PedidoItemEntity) null));
    }

    @Test
    @DisplayName("toResponseDTO(PedidoItem) returns valid DTO")
    void toResponseDTO_FromDomain_ShouldMapCorrectly() {
        var domain = PedidoItem.create(
                1L,
                1L,
                1L,
                2,
                LocalDateTime.parse("2024-10-10T12:00:00")
        );

        var dto = mapper.toResponseDTO(domain);

        assertNotNull(dto);
        assertEquals(1L, dto.id());
        assertEquals(1L, dto.pedidoId());
        assertEquals(1L, dto.menuItemId());
        assertEquals(2, dto.quantity());
        assertEquals(LocalDateTime.parse("2024-10-10T12:00:00"), dto.lastModifiedDate());
    }

    @Test
    @DisplayName("toResponseDTO(PedidoItem) throws exception on null")
    void toResponseDTO_FromDomain_ShouldThrowOnNull() {
        assertThrows(PedidoItemDomainException.class, () -> mapper.toResponseDTO(null));
    }

    @Test
    @DisplayName("toDomain(PedidoItemUpdateRequestDTO, Long) returns valid domain with ID")
    void toDomain_FromUpdateRequestDTO_ShouldMapCorrectly() {
        var updateDTO = new PedidoItemUpdateRequestDTO(
                1L,
                1L,
                2,
                LocalDateTime.parse("2024-10-10T12:00:00")
        );
        Long id = 555L;

        Long pedidoID = 88L;
        var domain = mapper.toDomain(updateDTO, id, pedidoID, 1L);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(1L, domain.getMenuItemId());
        assertEquals(2, domain.getQuantity());
        assertEquals(pedidoID, domain.getPedidoId());
    }   
    

    @Test
    @DisplayName("toDomain(PedidoItemUpdateRequestDTO, Long, Long) returns valid domain with ID and pedidoId and menuItemId")
    void toDomain_FromUpdateRequestDTOWitPedidoId_ShouldMapCorrectly() {
        var updateDTO = new PedidoItemUpdateRequestDTO(
                1L,
                1L,
                2,
                LocalDateTime.parse("2024-10-10T12:00:00")
        );
        Long id = 555L;
        Long pedidoId = 88L;
        Long menuItemId = 99L;

        var domain = mapper.toDomain(updateDTO, id, pedidoId, menuItemId);

        assertNotNull(domain);
        assertEquals(id, domain.getId());
        assertEquals(pedidoId, domain.getPedidoId());
        assertEquals(menuItemId, domain.getMenuItemId());
        assertEquals(2, domain.getQuantity());
    }

}
