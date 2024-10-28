package com.florend.restapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${com.florend.restapi.dev-url}")
    private String devUrl;

    @Value("${com.florend.restapi.prod-url}")
    private String prodUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in dev environment");

        Server prodServer = new Server();
        prodServer.setUrl(prodUrl);
        prodServer.setDescription("Server URL in prod environment");

        Contact contact = new Contact();
        contact.setName("florend");
        contact.setUrl("https://github.com/florend");
        contact.setEmail("2990283+florend@users.noreply.github.com");

        License license = new License()
                .name("Apache 2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");

        Info info = new Info()
                .title("REST API")
                .version("1.0")
                .description("Learning how to build a simple REST API with Spring Boot")
                .contact(contact)
                .license(license);

        return new OpenAPI().info(info).servers(List.of(devServer, prodServer));
    }
}