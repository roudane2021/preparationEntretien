package com.roudane.preparationentretien.controller.dto.user;

public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String email,
    String phone
) {}
