package pos.java.bora_comer.infra.delivery.order.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface SearchOrderControllerDocs {

    @Operation(
            summary = "Buscar Pedido por ID",
            description = "Endpoint para buscar um Pedido com base no ID fornecido."
    )
    @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id);

    @Operation(
            summary = "Buscar todos os pedidos",
            description = "Endpoint para buscar todos os pedidos com paginação."
    )
    @ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderResponseDTO.class)
            )
    )
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    ResponseEntity<List<OrderResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    );
}
