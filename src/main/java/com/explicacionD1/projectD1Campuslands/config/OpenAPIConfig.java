package com.explicacionD1.projectD1Campuslands.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                    .title("API documentada de campuslands para sistema de ventas")
                    .version("1.0")
                    .description("Esta API, se contruyo para explicar API RESTS en java"));

    }
}
