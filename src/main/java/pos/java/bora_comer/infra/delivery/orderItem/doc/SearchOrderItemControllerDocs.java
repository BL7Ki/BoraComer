package pos.java.bora_comer.infra.delivery.orderItem.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SearchOrderItemControllerDocs {

    @Operation(
            summary = "Buscar Item do Pedido por ID",
            description = "Endpoint para buscar um Item do Pedido com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Item do Pedido encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderItemResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Item do Pedido não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<OrderItemResponseDTO> findById(@PathVariable Long id);

    @Operation(
            summary = "Buscar todos os itens dos pedidos com paginação",
            description = "Endpoint para buscar todos os itens pedidos com paginação."
    )
    @ApiResponse(responseCode = "200", description = "Itens dos Pedidos encontrados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderItemResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<List<OrderItemResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
}
