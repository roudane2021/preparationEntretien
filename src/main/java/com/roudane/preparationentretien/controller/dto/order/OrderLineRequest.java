package com.roudane.preparationentretien.controller.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record OrderLineRequest(
    @NotBlank(message = "Le nom du produit est obligatoire") String productName,
    @NotNull(message = "La quantité est obligatoire") @Positive(message = "La quantité doit être positive") Integer quantity,
    @NotNull(message = "Le prix unitaire est obligatoire") @Positive(message = "Le prix unitaire doit être positif") BigDecimal unitPrice
) {}
