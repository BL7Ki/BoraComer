package pos.java.bora_comer.infra.delivery.pedidoItem.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemRequestDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;

public interface CreatePedidoItemControllerDocs {

    @Operation(
            summary = "Criar um novo item no pedido",
            description = "Endpoint para cadastrar um novo item no pedido, associando a um pedido e item de menu existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item de Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @RequestBody(
            required = true,
            description = "Dados para criação do Item no Pedido",
            content = @Content(schema = @Schema(implementation = PedidoItemRequestDTO.class))
    )
    ResponseEntity<PedidoItemResponseDTO> create(PedidoItemRequestDTO pedidoItemRequestDTO);
}
