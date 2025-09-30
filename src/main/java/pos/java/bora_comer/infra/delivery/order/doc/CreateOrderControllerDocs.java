package pos.java.bora_comer.infra.delivery.order.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pos.java.bora_comer.infra.delivery.order.dto.OrderRequestDTO;
import pos.java.bora_comer.infra.delivery.order.dto.OrderResponseDTO;

import org.springframework.http.ResponseEntity;

public interface CreateOrderControllerDocs {

    @Operation(
            summary = "Criar um novo pedido",
            description = "Endpoint para cadastrar um novo pedido, associando a um restaurante e usuário existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados para criação do Pedido",
            content = @Content(schema = @Schema(implementation = OrderRequestDTO.class))
    )
    ResponseEntity<OrderResponseDTO> create(OrderRequestDTO orderRequestDTO);
}
