package pos.java.bora_comer.infra.delivery.order.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderUpdateRequestDTO;

import org.springframework.http.ResponseEntity;


public interface UpdateOrderControllerDocs {

    @Operation(
            summary = "Atualizar Pedido por ID",
            description = "Endpoint para atualizar os dados de um pedido específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados atualizados do pedido",
            content = @Content(schema = @Schema(implementation = OrderUpdateRequestDTO.class))
    )
    ResponseEntity<OrderResponseDTO> update(Long id, OrderUpdateRequestDTO orderUpdateRequestDTO);
}
