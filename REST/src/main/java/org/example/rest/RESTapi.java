package org.example.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "User Service API",
                version = "1.0",
                description = "REST API для управления пользователями и отправки событий через Kafka"
        )
)
@SpringBootApplication(scanBasePackages = "org.example.rest")
public class RESTapi {
    public static void main(String[] args) {
        SpringApplication.run(RESTapi.class, args);
    }
}
//Документация по адресу
//http://localhost:8080/swagger-ui/index.html
