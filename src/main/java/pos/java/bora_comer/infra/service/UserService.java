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

    // Registers a new user with encrypted password.
    // Sets default role as DEFAULT and userTypeId as null.
    public UserEntity registerUser(String name, String email, String username, String rawPassword) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("User already exists with username: " + username);
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        UserEntity user = UserEntity.create(
                name,
                email,
                username,
                encodedPassword,
                null, // Optional AddressEntity
                UserRoleEntityEnum.DEFAULT,
                null // userTypeId, can be set if needed
        );

        return userRepository.save(user);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }
}
