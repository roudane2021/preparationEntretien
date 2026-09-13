package com.roudane.preparationentretien.controller.dto.order;

import java.math.BigDecimal;

public record OrderLineResponse(
    Long id,
    String productName,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal totalAmount
) {}
