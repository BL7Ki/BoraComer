package pos.java.bora_comer.core.usercase.login.impl;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.gateway.login.UserLoginGateway;
import pos.java.bora_comer.core.usercase.login.UserLoginUseCase;
import pos.java.bora_comer.infra.service.JwtService;

import java.util.Optional;

@Service
public class UserLoginUseCaseImpl implements UserLoginUseCase {

    private final UserLoginGateway userLoginGateway;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserLoginUseCaseImpl(UserLoginGateway userLoginGateway,
                                PasswordEncoder passwordEncoder,
                                JwtService jwtService) {
        this.userLoginGateway = userLoginGateway;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public String execute(String login, String password) {
        Optional<User> user = userLoginGateway.findByLogin(login);

        if (user.isEmpty()) {
            throw new BadCredentialsException("Usuário não encontrado");
        }

        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new BadCredentialsException("Senha inválida");
        }

        return jwtService.generateToken(user.get().getUsername());
    }
}
