package com.vocalharmonys.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers the "Authorize" JWT bearer scheme in Swagger UI, so a token from
 * {@code POST /api/auth/login} can be pasted in once and reused against every
 * protected endpoint tried from the UI. See {@link SecurityConfig} for which
 * routes actually require it.
 */
@Configuration
public class OpenApiConfig {

    private static final String BEARER_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI vocalHarmonysOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vocal Harmony's API")
                        .description("REST API backing the Vocal Harmony's choir site.")
                        .version("v0.1.0"))
                .addSecurityItem(new SecurityRequirement().addList(BEARER_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(BEARER_SCHEME_NAME, new SecurityScheme()
                                .name(BEARER_SCHEME_NAME)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
