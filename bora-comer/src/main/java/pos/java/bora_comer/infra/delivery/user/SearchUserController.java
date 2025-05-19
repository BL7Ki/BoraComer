package pos.java.bora_comer.infra.delivery.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.mapper.UserMapper;
import pos.java.bora_comer.core.usercase.SearchUserUseCase;
import pos.java.bora_comer.infra.delivery.user.dto.UserResponseDTO;

import java.util.List;

@RestController
@RequestMapping("/users")
public class SearchUserController {

    private final SearchUserUseCase searchUserUseCase;
    private final UserMapper userMapper;

    public SearchUserController(SearchUserUseCase searchUserUseCase, UserMapper userMapper) {
        this.searchUserUseCase = searchUserUseCase;
        this.userMapper = userMapper;
    }

    @Operation(
            summary = "Buscar usuário por ID",
            description = "Endpoint para buscar um usuário com base no ID fornecido.")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    @ApiResponse(content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserResponseDTO.class)
    ))
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        var user = searchUserUseCase.findById(id);
        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }

    @Operation(
            summary = "Buscar todos os usuários",
            description = "Endpoint para buscar todos os usuários com paginação."
    )
    @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserResponseDTO.class)
    ))
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {

        Page<User> users = searchUserUseCase.findAll(page, size);
        List<UserResponseDTO> usuarios = users.stream()
                .map(userMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(usuarios);
    }
}
