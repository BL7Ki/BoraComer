package pos.java.bora_comer.infra.delivery.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pos.java.bora_comer.core.usercase.login.UserLoginUseCase;
import pos.java.bora_comer.infra.delivery.login.dto.LoginRequestDTO;
import pos.java.bora_comer.infra.delivery.login.dto.LoginResponseDTO;
import pos.java.bora_comer.infra.delivery.user.doc.LoginControllerDocs;

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerDocs {

    private final UserLoginUseCase userLoginUseCase;

    public LoginController(UserLoginUseCase userLoginUseCase) {
        this.userLoginUseCase = userLoginUseCase;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        String token = userLoginUseCase.execute(loginRequestDTO.login(), loginRequestDTO.password());
        return ResponseEntity.ok(new LoginResponseDTO(token, "Bearer"));
    }
}
