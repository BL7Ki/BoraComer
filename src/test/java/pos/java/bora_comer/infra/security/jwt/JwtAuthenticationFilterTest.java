package pos.java.bora_comer.infra.security.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import pos.java.bora_comer.infra.service.CustomUserDetailsService;
import pos.java.bora_comer.infra.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String VALID_TOKEN = "valid.jwt.token";
    private static final String USERNAME = "testuser";
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        userDetails = new User(USERNAME, "pass", Collections.emptyList());

        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilter_shouldPassChain_whenNoAuthorizationHeader() throws Exception {
        // Setup: Header nulo
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verificação: A cadeia de filtros deve ser chamada e nenhuma interação com services
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void doFilter_shouldPassChain_whenAuthorizationHeaderInvalid() throws Exception {
        // Setup: Header inválido (não começa com "Bearer ")
        when(request.getHeader("Authorization")).thenReturn("Token " + VALID_TOKEN);

        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verificação: A cadeia de filtros deve ser chamada e nenhuma interação com services
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(jwtService);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    void doFilter_shouldAuthenticateUser_whenTokenIsValid() throws Exception {
        // Setup: Token Válido
        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_TOKEN);
        when(jwtService.extractUsername(VALID_TOKEN)).thenReturn(USERNAME);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        when(jwtService.isTokenValid(VALID_TOKEN, USERNAME)).thenReturn(true);

        // Garante que o contexto está vazio
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verificação 1: O contexto de segurança deve ter sido preenchido
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        assertEquals(USERNAME, authentication.getName());

        // Verificação 2: A cadeia de filtros é chamada
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilter_shouldNotAuthenticateUser_whenTokenIsInvalid() throws Exception {
        // Setup: Token Inválido
        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_TOKEN);
        when(jwtService.extractUsername(VALID_TOKEN)).thenReturn(USERNAME);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
        when(jwtService.isTokenValid(VALID_TOKEN, USERNAME)).thenReturn(false); // Token inválido

        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verificação: O contexto de segurança deve permanecer vazio
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Verificação 2: A cadeia de filtros é chamada
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void doFilter_shouldNotAuthenticateUser_ifAlreadyAuthenticated() throws Exception {
        // Setup: Simula um usuário já autenticado no contexto
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(mock(Authentication.class));
        SecurityContextHolder.setContext(securityContext);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_TOKEN);

        jwtAuthenticationFilter.doFilter(request, response, filterChain);

        // Verificação: Garante que nenhuma tentativa de autenticação foi feita
        verify(jwtService, never()).extractUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }
}