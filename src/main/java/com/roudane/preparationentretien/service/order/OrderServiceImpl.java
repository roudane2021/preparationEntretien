package com.roudane.preparationentretien.service.order;

import com.roudane.preparationentretien.domain.order.Order;
import com.roudane.preparationentretien.domain.user.User;
import com.roudane.preparationentretien.dto.order.OrderRequest;
import com.roudane.preparationentretien.dto.order.OrderResponse;
import com.roudane.preparationentretien.exception.ResourceNotFoundException;
import com.roudane.preparationentretien.mapper.OrderMapper;
import com.roudane.preparationentretien.repository.OrderRepository;
import com.roudane.preparationentretien.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new ResourceNotFoundException("User", request.userId()));

        Order order = orderMapper.toEntity(request, user);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(orderMapper::toResponse)
            .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return orderMapper.toResponse(order);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order", id);
        }
        orderRepository.deleteById(id);
    }
}
