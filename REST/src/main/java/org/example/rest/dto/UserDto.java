package org.example.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Пользователь системы")
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @Schema(description = "Имя пользователя", example = "Иван Иванов")
    private String name;
    @Schema(description = "Email пользователя", example = "user@example.com")
    private String email;
}
