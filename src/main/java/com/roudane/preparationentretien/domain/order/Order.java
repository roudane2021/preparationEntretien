package com.roudane.preparationentretien.domain.order;

import com.roudane.preparationentretien.domain.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderLine> orderLines = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        recalculateTotal();
    }

    @PreUpdate
    protected void onUpdate() {
        recalculateTotal();
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrder(this);
        recalculateTotal();
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
        orderLine.setOrder(null);
        recalculateTotal();
    }

    public void recalculateTotal() {
        if (orderLines == null || orderLines.isEmpty()) {
            this.totalAmount = BigDecimal.ZERO;
            return;
        }

        this.totalAmount = orderLines.stream()
            .map(OrderLine::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
