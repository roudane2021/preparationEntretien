package com.roudane.preparationentretien.dto.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequest(
    @NotNull(message = "L'identifiant utilisateur est obligatoire") Long userId,
    @NotEmpty(message = "La commande doit contenir au moins une ligne") List<OrderLineRequest> lines
) {}
