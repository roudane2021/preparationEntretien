package com.roudane.preparationentretien.controller.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
    @NotBlank(message = "Le prénom est obligatoire") String firstName,
    @NotBlank(message = "Le nom est obligatoire") String lastName,
    @NotBlank(message = "L'email est obligatoire") @Email(message = "L'email doit être valide") String email,
    String phone
) {}
