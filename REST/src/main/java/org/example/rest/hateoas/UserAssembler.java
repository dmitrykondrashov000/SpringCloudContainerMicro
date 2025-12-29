package org.example.rest.hateoas;

import org.example.rest.controller.UserController;
import org.example.rest.dto.UserDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserAssembler {

    public EntityModel<UserDto> toModel(UserDto userDto) {
        return EntityModel.of(
                userDto,
                linkTo(methodOn(UserController.class).getById(userDto.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getAll()).withRel(IanaLinkRelations.COLLECTION),
                linkTo(methodOn(UserController.class)
                        .update(userDto.getId(), userDto))
                        .withRel("update"),
                linkTo(methodOn(UserController.class)
                        .delete(userDto.getId()))
                        .withRel("delete")
        );
    }
}