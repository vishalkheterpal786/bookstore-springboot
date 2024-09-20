package com.katachallenge.bookstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customSwaggerConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("Api Documentation for Sale Service")
                        .description("Api Documentation for Sale Service")
                        .version("1.0")
                        .termsOfService("urn:tos")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
                );
    }
}