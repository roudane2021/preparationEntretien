package com.roudane.preparationentretien.mapper;

import com.roudane.preparationentretien.domain.user.User;
import com.roudane.preparationentretien.dto.user.UserRequest;
import com.roudane.preparationentretien.dto.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {
        if (request == null) {
            return null;
        }

        return User.builder()
            .firstName(request.firstName())
            .lastName(request.lastName())
            .email(request.email())
            .phone(request.phone())
            .build();
    }

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            user.getPhone()
        );
    }

    public void updateEntity(User user, UserRequest request) {
        if (user == null || request == null) {
            return;
        }

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPhone(request.phone());
    }
}
