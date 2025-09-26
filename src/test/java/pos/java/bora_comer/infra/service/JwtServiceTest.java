package pos.java.bora_comer.infra.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.domain.user.UserRoleEnum;
import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private User testUser;
    private String validToken;
    private static final String TEST_USERNAME = "messi_jwt";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        testUser = User.create(
                "Lionel",
                "lionel@ex.com",
                TEST_USERNAME,
                "senha_segura",
                null, // Address
                UserRoleEnum.DEFAULT,
                "2025-01-01",
                null,
                UserTypeNameEnum.DONO_RESTAURANTE
        );

        validToken = jwtService.generateToken(testUser);
    }

    @Test
    void testGenerateToken_shouldCreateValidToken() {
        assertNotNull(validToken);
        // Verifica o formato básico do JWT (Header.Payload.Signature)
        assertEquals(3, validToken.split("\\.").length);
    }

    @Test
    void testExtractUsername_shouldReturnCorrectUsername() {
        String username = jwtService.extractUsername(validToken);
        assertEquals(TEST_USERNAME, username);
    }

    @Test
    void testIsTokenValid_shouldReturnTrueForValidToken() {
        // Valida o token contra o username que o gerou
        assertTrue(jwtService.isTokenValid(validToken, TEST_USERNAME));
    }

    @Test
    void testIsTokenValid_shouldReturnFalseForInvalidUsername() {
        // Valida o token contra um username diferente
        assertFalse(jwtService.isTokenValid(validToken, "wronguser"));
    }

    @Test
    void testIsTokenValid_shouldReturnFalseForEmptyToken() {
        assertFalse(jwtService.isTokenValid("invalid.token.signature", TEST_USERNAME));
    }
}