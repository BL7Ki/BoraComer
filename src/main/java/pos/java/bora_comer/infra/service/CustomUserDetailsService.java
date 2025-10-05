package pos.java.bora_comer.infra.service;

import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.infra.security.auth.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserSearchGateway userSearchGateway;

    // 💡 Injeta o Gateway, não o Repository!
    public CustomUserDetailsService(UserSearchGateway userSearchGateway) {
        this.userSearchGateway = userSearchGateway;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            // 1. Busca o objeto de Domínio (User) através do Gateway
            User user = userSearchGateway.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            // 2. Retorna o adaptador que encapsula o Domínio
            return new CustomUserDetails(user);

        } catch (SummerNotFoundException e) {
            // Se o Gateway lançar sua exceção (SummerNotFoundException),
            // a camada de segurança deve traduzi-la para a exceção padrão do Spring Security.
            throw new UsernameNotFoundException("User not found: " + username, e);
        }
    }
}