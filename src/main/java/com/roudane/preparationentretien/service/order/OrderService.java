package com.roudane.preparationentretien.service.order;

import com.roudane.preparationentretien.domain.order.OrderDomain;
import java.util.List;

public interface OrderService {

    OrderDomain createOrder(OrderDomain orderDomain);

    List<OrderDomain> getAllOrders();

    OrderDomain getOrderById(Long id);

    void deleteOrder(Long id);
}
