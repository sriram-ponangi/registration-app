package com.example.registration.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class OpenAPIConfig {

    private final String appUrl;
    private final String environment;

    public OpenAPIConfig(@Value("${app.url}") String appUrl,
                         @Value("${app.environment}") String environment) {
        this.appUrl = appUrl;
        this.environment = environment;
    }

    @Bean
    public OpenAPI createOpenAPI() {
        List<Server> servers = new ArrayList<>(1);
        servers.add(
                new Server()
                        .url(appUrl)
                        .description("Server URL in " + environment + " environment")
        );

        Contact contact = new Contact().email("dummy-address@gmail.com")
                .url("http://localhost/dummy-url")
                .name("Sriram Ponangi");

        Info info = new Info()
                .title("User Registration API")
                .version("v1.0.0")
                .contact(contact)
                .description("This API exposes endpoints to manage user registration based on IP address.")
                .termsOfService("http://localhost/dummy-url/terms")
                .summary("This API exposes endpoints to manage user registration based on IP address.")
                .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"));

        return new OpenAPI().info(info).servers(servers);
    }
}
