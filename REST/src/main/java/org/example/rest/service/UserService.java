package org.example.rest.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.example.rest.dto.UserDto;
import org.example.rest.entity.User;
import org.example.rest.event.UserEvent;
import org.example.rest.repo.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserApiService {

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;
    private final UserRepository repository;

    // === Основные методы ===
    @Override
    public List<UserDto> getAll() {
        return repository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDto create(UserDto dto) throws JsonProcessingException {
        User user = repository.save(toEntity(dto));
        sendUserEvent("CREATE", user.getEmail());      // ← добавлен CircuitBreaker
        return toDto(user);
    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return toDto(repository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        repository.delete(user);
        sendUserEvent("DELETE", user.getEmail());       // ← добавлен CircuitBreaker
    }

    // === Circuit Breaker обёртка ===
    @CircuitBreaker(name = "kafkaCB", fallbackMethod = "fallbackKafkaSend")
    public void sendUserEvent(String action, String email) {
        UserEvent event = new UserEvent(action, email);
        kafkaTemplate.send("user-events-json111", event);
        log.info("Отправлено событие [{}] пользователя {}", action, email);
    }

    // fallback должен совпадать по параметрам + Throwable
    public void fallbackKafkaSend(String action, String email, Throwable ex) {
        log.error("Kafka недоступна. Событие [{}] для {} не отправлено. Ошибка: {}", action, email, ex.getMessage());
    }

    // === Мапперы ===
    private UserDto toDto(User u) {
        UserDto dto = new UserDto();
        dto.setId(u.getId());
        dto.setName(u.getName());
        dto.setEmail(u.getEmail());
        return dto;
    }

    private User toEntity(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
    }
}

