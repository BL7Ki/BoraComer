package pos.java.bora_comer.infra.delivery.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.core.usercase.login.UserLoginUseCase;
import pos.java.bora_comer.core.domain.LoginResponseEnum;
import pos.java.bora_comer.infra.delivery.login.dto.LoginRequestDTO;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserLoginUseCase userLoginUseCase;

    public LoginController(UserLoginUseCase userLoginUseCase) {
        this.userLoginUseCase = userLoginUseCase;
    }

    @Operation(
            summary = "Validar login",
            description = "Endpoint para validar o login do usuário com base no nome de usuário e senha fornecidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login válido"),
            @ApiResponse(responseCode = "401", description = "Login inválido"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor"),
            @ApiResponse(content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LoginRequestDTO.class)
            ))
    })
    @PostMapping
    public ResponseEntity<String> validateLogin(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        LoginResponseEnum result = userLoginUseCase.execute(loginRequestDTO.login(), loginRequestDTO.password());

        if (result == LoginResponseEnum.SUCCESS) {
            return ResponseEntity.ok(result.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result.getMessage());
        }
    }
}
