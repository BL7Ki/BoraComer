package pos.java.bora_comer.infra.service;

import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

     //Cadastra um novo usuario com senha criptografada.
     //Define a role padrão como DEFAULT e userTypeId como null.
    public UserEntity registerUser(String name, String email, String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Usuário já existe com esse username: " + username);
        }

        // Criptografa a senha
        String encodedPassword = passwordEncoder.encode(rawPassword);

        UserEntity user = UserEntity.create(
                name,
                email,
                username,
                encodedPassword,
                null, // AddressEntity opcional, pode ser null
                UserRoleEntityEnum.DEFAULT,
                null // userTypeId, você pode passar se quiser vincular tipo de usuario
        );

        return userRepository.save(user);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + username));
    }
}
