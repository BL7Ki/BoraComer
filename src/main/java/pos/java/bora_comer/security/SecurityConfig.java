package pos.java.bora_comer.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pos.java.bora_comer.infra.service.CustomUserDetailsService;
import pos.java.bora_comer.infra.security.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter,
                          CustomUserDetailsService customUserDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,  JwtAuthenticationFilter jwtAuthFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/login",
                                "/health",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()
                        /*

                                                        DONO_RESTAURANTE: acesso total à gestão do restaurante e cardápio.
                                                        CLIENTE: acesso à visualização e pedidos.
                                                        Todos os endpoints devem ser protegidos por JWT e roles.
                         */

                        // restaurantes
                        .requestMatchers(HttpMethod.POST, "/restaurants/**").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.PUT, "/restaurants/**").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.DELETE, "/restaurants/**").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.GET, "/restaurants/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")


                        // usuarios
                        .requestMatchers("/users/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")

                        // cardápio
                        .requestMatchers(HttpMethod.POST,"/menu-items/**").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.PUT,"/menu-items/**").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.DELETE,"/menu-items/**").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.GET,"/menu-items/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")

                        // pedidos
                        .requestMatchers(HttpMethod.GET, "/orders/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/orders/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/orders/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/orders/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")


                        // Itens do pedido
                        .requestMatchers(HttpMethod.GET, "/orderitems/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/orderitems/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/orderitems/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/orderitems/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")

                        // reservas
                        .requestMatchers(HttpMethod.GET, "/reserves/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.POST, "/reserves/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/reserves/**").hasAnyAuthority("DONO_RESTAURANTE", "CLIENTE")
                        .requestMatchers(HttpMethod.DELETE, "/reserves/**").hasAuthority("DONO_RESTAURANTE")

                        // tipos de usuarios
                        .requestMatchers( "/user-types/**").hasAuthority("DONO_RESTAURANTE")

                        .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
