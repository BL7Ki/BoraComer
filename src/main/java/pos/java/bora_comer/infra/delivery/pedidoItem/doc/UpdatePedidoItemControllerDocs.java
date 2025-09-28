package pos.java.bora_comer.infra.delivery.pedidoItem.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemResponseDTO;
import pos.java.bora_comer.infra.delivery.pedidoItem.dto.PedidoItemUpdateRequestDTO;

import org.springframework.http.ResponseEntity;


public interface UpdatePedidoItemControllerDocs {

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
            content = @Content(schema = @Schema(implementation = PedidoItemUpdateRequestDTO.class))
    )
    ResponseEntity<PedidoItemResponseDTO> update(Long id, PedidoItemUpdateRequestDTO pedidoItemUpdateRequestDTO);
}
