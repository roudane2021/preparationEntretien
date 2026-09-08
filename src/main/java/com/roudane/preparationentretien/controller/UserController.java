package com.roudane.preparationentretien.controller;

import com.roudane.preparationentretien.domain.user.UserDomain;
import com.roudane.preparationentretien.dto.user.UserRequest;
import com.roudane.preparationentretien.dto.user.UserResponse;
import com.roudane.preparationentretien.mapper.UserWebMapper;
import com.roudane.preparationentretien.service.user.UserService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserWebMapper userWebMapper;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers().stream()
            .map(userWebMapper::toResponse)
            .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserDomain domain = userService.getUserById(id);
        return ResponseEntity.ok(userWebMapper.toResponse(domain));
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserDomain domainToCreate = userWebMapper.toDomain(request);
        UserDomain createdDomain = userService.createUser(domainToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(userWebMapper.toResponse(createdDomain));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        UserDomain domainToUpdate = userWebMapper.toDomain(request);
        UserDomain updatedDomain = userService.updateUser(id, domainToUpdate);
        return ResponseEntity.ok(userWebMapper.toResponse(updatedDomain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
