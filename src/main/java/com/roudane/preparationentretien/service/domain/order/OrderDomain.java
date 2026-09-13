package com.roudane.preparationentretien.service.domain.order;

import com.roudane.preparationentretien.domain.user.UserDomain;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDomain {

    private Long id;
    private Long userId;
    private UserDomain user;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;

    @Builder.Default
    private List<OrderLineDomain> orderLines = new ArrayList<>();

    public void recalculateTotal() {
        if (orderLines == null || orderLines.isEmpty()) {
            this.totalAmount = BigDecimal.ZERO;
            return;
        }

        this.totalAmount = orderLines.stream()
            .map(OrderLineDomain::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
