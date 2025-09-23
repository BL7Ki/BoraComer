package pos.java.bora_comer.infra.security.auth;

import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// vai buscar os detalhes do user quando tentar se conectar
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + login));

        String role = userEntity.getUserTypeEntity().getName().name();
        // .getName() deve retornar o enum UserTypeNameEntityEnum, então usamos .name() para transformar em String ("ADMIN", "CLIENT", etc.)

        return org.springframework.security.core.userdetails.User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .authorities("ROLE_" + role) // Spring Security espera roles prefixadas com "ROLE_"
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
