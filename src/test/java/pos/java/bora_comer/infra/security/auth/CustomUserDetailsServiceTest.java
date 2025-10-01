package pos.java.bora_comer.infra.security.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserRoleEntityEnum;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private static final String TEST_LOGIN = "testuser";
    private static final String TEST_PASSWORD = "encoded_password";

    // Apenas a enum é necessária para os mocks
    // Assumindo que UserRoleEntityEnum está em pos.java.bora_comer.infra.persistence.repository.user.entity
    // O mock de UserEntity será criado dentro dos métodos de teste

    @BeforeEach
    void setUp() {
        // Inicialização de mocks é tratada por @Mock e @InjectMocks
    }

    private UserEntity setupMockUser(String login, UserRoleEntityEnum role) {
        // Cria e configura um mock de UserEntity
        UserEntity mockUser = mock(UserEntity.class);
        when(mockUser.getUsername()).thenReturn(login);
        when(mockUser.getPassword()).thenReturn(TEST_PASSWORD);
        when(mockUser.getRole()).thenReturn(role);
        return mockUser;
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetails_forClient() {
        // Setup
        UserEntity clientUser = setupMockUser(TEST_LOGIN, UserRoleEntityEnum.DEFAULT);
        when(userRepository.findByUsername(TEST_LOGIN)).thenReturn(Optional.of(clientUser));

        // Ação
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(TEST_LOGIN);

        // Verificação
        assertNotNull(userDetails);
        assertEquals(TEST_LOGIN, userDetails.getUsername());
        assertEquals(TEST_PASSWORD, userDetails.getPassword());

        assertFalse(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT"))
        );
    }

    @Test
    void loadUserByUsername_shouldReturnUserDetailsWithCorrectRole_forAdmin() {
        // Setup
        String adminLogin = "adminuser";
        UserEntity adminUser = setupMockUser(adminLogin, UserRoleEntityEnum.ADMIN);
        when(userRepository.findByUsername(adminLogin)).thenReturn(Optional.of(adminUser));

        // Ação
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(adminLogin);

        // Verificação
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
        );
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {
        // Setup
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Ação & Verificação
        assertThrows(UsernameNotFoundException.class, () ->
                customUserDetailsService.loadUserByUsername("nonexistentuser")
        );
    }
}