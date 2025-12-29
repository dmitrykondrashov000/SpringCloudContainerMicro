package org.example.rest.controller;

import org.example.rest.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "User API",
        description = "CRUD операции для работы с пользователями"
)
@RequestMapping("/users")
public interface UserApiController {

    @Operation(
            summary = "Получить список всех пользователей",
            description = "Возвращает коллекцию пользователей с навигационными ссылками (_links).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Список пользователей получен успешно",
                            content = @Content(mediaType = "application/hal+json",
                                    schema = @Schema(implementation = UserDto.class))
                    )
            }
    )
    @GetMapping
    ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAll();


    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает данные пользователя по его идентификатору.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь найден",
                            content = @Content(mediaType = "application/hal+json",
                                    schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<EntityModel<UserDto>> getById(@PathVariable Long id);


    @Operation(
            summary = "Добавить пользователя",
            description = "Создаёт нового пользователя и возвращает его данные.",
            requestBody = @RequestBody(
                    description = "Информация о новом пользователе",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пользователь успешно создан",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    )
            }
    )
    @PostMapping
    ResponseEntity<EntityModel<UserDto>> create(@org.springframework.web.bind.annotation.RequestBody UserDto user) throws JsonProcessingException;


    @Operation(
            summary = "Изменить данные пользователя",
            description = "Обновляет данные пользователя по его ID.",
            requestBody = @RequestBody(
                    description = "Новые данные пользователя",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDto.class))
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь обновлён",
                            content = @Content(schema = @Schema(implementation = UserDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<EntityModel<UserDto>> update(@PathVariable Long id,
                                                @org.springframework.web.bind.annotation.RequestBody UserDto user);


    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя по идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь успешно удалён"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Long id);
}