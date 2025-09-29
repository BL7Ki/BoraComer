package pos.java.bora_comer.infra.delivery.orderItem.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemResponseDTO;
import pos.java.bora_comer.infra.delivery.orderItem.dto.OrderItemUpdateRequestDTO;

import org.springframework.http.ResponseEntity;


public interface UpdateOrderItemControllerDocs {

    @Operation(
            summary = "Atualizar Item do Pedido por ID",
            description = "Endpoint para atualizar os dados de um item do pedido específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item do Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Item do Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados atualizados do item do pedido",
            content = @Content(schema = @Schema(implementation = OrderItemUpdateRequestDTO.class))
    )
    ResponseEntity<OrderItemResponseDTO> update(Long id, OrderItemUpdateRequestDTO orderItemUpdateRequestDTO);
}
