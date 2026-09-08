package com.roudane.preparationentretien.service.order;

import com.roudane.preparationentretien.dto.order.OrderRequest;
import com.roudane.preparationentretien.dto.order.OrderResponse;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    void deleteOrder(Long id);
}
