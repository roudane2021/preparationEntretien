package com.roudane.preparationentretien.mapper;

import com.roudane.preparationentretien.domain.order.Order;
import com.roudane.preparationentretien.domain.order.OrderLine;
import com.roudane.preparationentretien.domain.user.User;
import com.roudane.preparationentretien.dto.order.OrderLineResponse;
import com.roudane.preparationentretien.dto.order.OrderRequest;
import com.roudane.preparationentretien.dto.order.OrderResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequest request, User user) {
        if (request == null) {
            return null;
        }

        Order order = Order.builder()
            .user(user)
            .orderLines(new ArrayList<>())
            .build();

        List<OrderLine> orderLines = request.lines().stream()
            .map(lineRequest -> {
                OrderLine orderLine = OrderLine.builder()
                    .order(order)
                    .productName(lineRequest.productName())
                    .quantity(lineRequest.quantity())
                    .unitPrice(lineRequest.unitPrice())
                    .build();
                return orderLine;
            })
            .toList();

        order.setOrderLines(new ArrayList<>(orderLines));
        order.recalculateTotal();
        return order;
    }

    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderLineResponse> lines = order.getOrderLines() == null
            ? List.of()
            : order.getOrderLines().stream()
                .map(orderLine -> new OrderLineResponse(
                    orderLine.getId(),
                    orderLine.getProductName(),
                    orderLine.getQuantity(),
                    orderLine.getUnitPrice(),
                    orderLine.getLineTotal()
                ))
                .toList();

        String customerName = order.getUser() == null
            ? null
            : order.getUser().getFirstName() + " " + order.getUser().getLastName();

        return new OrderResponse(
            order.getId(),
            order.getUser() != null ? order.getUser().getId() : null,
            customerName,
            order.getCreatedAt(),
            order.getTotalAmount(),
            lines
        );
    }
}
