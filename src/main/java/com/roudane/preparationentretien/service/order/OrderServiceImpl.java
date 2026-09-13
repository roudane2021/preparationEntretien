package com.roudane.preparationentretien.service.order;

import com.roudane.preparationentretien.domain.order.OrderDomain;
import com.roudane.preparationentretien.repository.entity.OrderEntity;
import com.roudane.preparationentretien.repository.entity.UserEntity;
import com.roudane.preparationentretien.exception.ResourceNotFoundException;
import com.roudane.preparationentretien.service.mapper.OrderEntityMapper;
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
    private final OrderEntityMapper orderEntityMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDomain createOrder(OrderDomain orderDomain) throws Exception {
        Long userId = orderDomain.getUserId();
        UserEntity userEntity = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        OrderEntity orderEntity = orderEntityMapper.toEntity(orderDomain);
        orderEntity.setUser(userEntity);

        if (orderEntity.getOrderLines() != null) {
            orderEntity.getOrderLines().forEach(line -> line.setOrder(orderEntity));
        }

        OrderEntity savedOrder = orderRepository.save(orderEntity);

        if(true) {
            throw  new Exception("Test Exception");
        }
        return orderEntityMapper.toDomain(savedOrder);
    }

    @Override
    public List<OrderDomain> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(orderEntityMapper::toDomain)
            .toList();
    }



    @Override
    public OrderDomain getOrderById(Long id) {
        OrderEntity orderEntity = orderRepository.findByIdWithDetails(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order", id));
        return orderEntityMapper.toDomain(orderEntity);
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
