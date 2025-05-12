package pos.java.bora_comer.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI locatech() {
        return new OpenAPI()
                .info(
                        new Info().title("Bora Comer API")
                                .description("BoraComer - Sistema de Delivery e Gestão para Restaurantes")
                                .version("v1.0.0")
                                .license(new License().name("Bora Comer 1.0").url("https://github.com/BL7Ki/BoraComer"))
                );
    }
}
