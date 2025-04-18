package school.sptech.Simbiosys.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Simbiosys API",
                version = "1.0",
                description = "Documentação API REST"
        ),
        security = @SecurityRequirement(name = "Bearer")
)

@SecurityScheme(
        name = "Bearer", type   = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("spring-boot")
                .pathsToMatch("/**")
                .build();
    }
}
