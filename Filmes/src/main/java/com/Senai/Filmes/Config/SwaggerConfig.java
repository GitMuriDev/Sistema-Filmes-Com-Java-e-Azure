package com.Senai.Filmes.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customuzindaAPI(){
        Server servidorLocal = new Server();
        servidorLocal.setUrl("http://localhost:8080");
        servidorLocal.setDescription("Servidor de desenvolvimento local");

        Server producao = new Server();
        producao.setUrl("https://software-cinema-api-azure.azurewubsite.net");
        producao.setDescription("Servidor de produção Https");

        return new OpenAPI().info(new Info()
                        .title("Softtware Cinema API")
                        .version("v1.0")
                        .description("API para gerenciamento de cinema com reservas e sessões"))
                .servers(List.of(servidorLocal, producao))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", SchemaSeguranca()));
    }

    private SecurityScheme SchemaSeguranca(){
        return new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}