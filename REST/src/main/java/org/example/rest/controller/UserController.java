package org.example.rest.controller;

import org.example.rest.dto.UserDto;
import org.example.rest.hateoas.UserAssembler;
import org.example.rest.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements UserApiController {

    private final UserService service;
    private final UserAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDto>>> getAll() {
        List<EntityModel<UserDto>> users = service.getAll()
                .stream()
                .map(assembler::toModel)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> getById(@PathVariable Long id) {
        UserDto user = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(user));
    }


    @PostMapping
    public ResponseEntity<EntityModel<UserDto>> create(@RequestBody UserDto dto) throws JsonProcessingException {
        UserDto created = service.create(dto);
        return ResponseEntity.ok(assembler.toModel(created));
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDto>> update(@PathVariable Long id, @RequestBody UserDto dto) {
        UserDto updated = service.update(id, dto);
        return ResponseEntity.ok(assembler.toModel(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
