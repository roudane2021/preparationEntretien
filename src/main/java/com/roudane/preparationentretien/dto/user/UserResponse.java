package com.roudane.preparationentretien.dto.user;

public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone
) {}
