package io.artur.coffeemachine.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SwaggerConfig class is responsible for configuring Swagger/OpenAPI documentation
 * for the Coffee Machine API. This configuration ensures that an OpenAPI definition
 * is generated for the API, making it easier to explore and test the various endpoints.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates and configures the OpenAPI object for Swagger documentation.
     *
     * <p>This method defines the basic information for the API, including the title,
     * version, and description.</p>
     *
     * @return an {@link OpenAPI} object with the configured metadata for the Coffee Machine API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Coffee Machine API")
                        .version("1.0")
                        .description("API for Coffee Machine")
                );
    }
}
