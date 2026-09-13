package com.roudane.preparationentretien.controller;


import com.roudane.preparationentretien.controller.dto.order.OrderRequest;
import com.roudane.preparationentretien.controller.dto.order.OrderResponse;
import com.roudane.preparationentretien.controller.mapper.OrderWebMapper;
import com.roudane.preparationentretien.service.domain.order.OrderDomain;
import com.roudane.preparationentretien.service.order.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderWebMapper orderWebMapper;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders().stream()
            .map(orderWebMapper::toResponse)
            .toList();
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        OrderDomain domain = orderService.getOrderById(id);
        return ResponseEntity.ok(orderWebMapper.toResponse(domain));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request)throws Exception {
        OrderDomain domainToCreate = orderWebMapper.toDomain(request);
        domainToCreate.setUserId(request.userId());
        OrderDomain createdDomain = orderService.createOrder(domainToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderWebMapper.toResponse(createdDomain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
