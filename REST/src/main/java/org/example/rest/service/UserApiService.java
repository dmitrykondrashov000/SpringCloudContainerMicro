package org.example.rest.service;

import org.example.rest.dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserApiService {
    List<UserDto> getAll();
    UserDto getById(Long id);
    UserDto create(UserDto dto) throws JsonProcessingException;
    UserDto update(Long id, UserDto dto);
    void delete(Long id);
}
